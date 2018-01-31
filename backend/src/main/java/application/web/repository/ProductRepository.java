package main.java.application.web.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import main.java.application.web.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	Product findById(Long id);

}
