package main.java.application.web.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import main.java.application.web.model.ProductType;

@Repository
public interface ProductTypeRepository extends CrudRepository<ProductType, Long> {
	ProductType findById(Long id);
}
