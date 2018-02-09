package main.java.application.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import main.java.application.web.model.products.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Product findById(Long id);

}
