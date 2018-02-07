package main.java.application.web.controller;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import main.java.application.web.model.Product;
import main.java.application.web.model.ProductType;
import main.java.application.web.repository.ProductRepository;
import main.java.application.web.repository.ProductTypeRepository;

@RestController
@RequestMapping(path = "/products")
public class ProductRest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRest.class);

	@Autowired
	private ProductTypeRepository productTypeRepository;

	@Autowired
	private ProductRepository productRepository;
	
	 /*--------------------------------Create operation ----------------------------*/
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product forCreate) {
        LOGGER.info("create product with data: "+forCreate.toString());
        ProductType type = new ProductType();
        type = productTypeRepository.findById(forCreate.getType().getId());
        forCreate.setType(type);
        Product result = productRepository.save(forCreate);
        LOGGER.info("creating of new account completed");
        return new ResponseEntity<Product>(result,HttpStatus.OK);
    }
	
	/*--------------------------------Retrieve all ----------------------------*/
    @GetMapping()
    public @ResponseBody Iterable<Product> listAllProducts() {
        return productRepository.findAll();
    }
    
    /*--------------------------------Single operation ----------------------------*/
    @SuppressWarnings("unchecked")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") long id) {
        LOGGER.info("Fetching product with id {}", id);
        Product product = productRepository.findById(id);
        if (product == null) {
            LOGGER.error("product with id {} not found.", id);
            return new ResponseEntity(new EntityNotFoundException("Product with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

}
