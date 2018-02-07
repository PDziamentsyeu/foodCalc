package main.java.application.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
	private String name;
	
	private int caloricity;
	
	@ManyToOne
	@JoinColumn(name = "product")
	private ProductType type;

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

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", caloricity=" + caloricity + ", type=" + type + "]";
	}
	
	

}
