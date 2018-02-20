package main.java.application.web.model.products;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product_types")
public class ProductType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
	private String productType;
	
	@OneToMany(mappedBy = "productType",cascade = CascadeType.ALL)
	private List<Product> product;
	
	public ProductType() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String type) {
		this.productType = type;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "ProductType [id=" + id + ", type=" + productType + ", product=" + product + "]";
	}
	
	
	
	
}
