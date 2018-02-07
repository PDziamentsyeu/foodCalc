package main.java.application.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import main.java.application.web.model.ProductType;
import main.java.application.web.repository.ProductTypeRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/productTypes")
public class ProductTypeRest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeRest.class);

	@Autowired
	private ProductTypeRepository productTypeRepository;

	//TODO put all data by hardcode cuz it is directory table
	
	 /*--------------------------------Retrieve all ----------------------------*/
    @GetMapping()
    public @ResponseBody Iterable<ProductType> listAllProductTypes() {
    	LOGGER.info("get all prduct types");
        return productTypeRepository.findAll();
    }
}
