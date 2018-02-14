package main.java.application.web.model.products;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
	private String name;
	
	private int caloricity;
	
	@ManyToOne
	@JoinColumn(name = "productType")
	private ProductType productType;

	public Product() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCaloricity() {
		return caloricity;
	}

	public void setCaloricity(int caloricity) {
		this.caloricity = caloricity;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType type) {
		this.productType = type;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", caloricity=" + caloricity + ", type=" + productType + "]";
	}
	
	

}
