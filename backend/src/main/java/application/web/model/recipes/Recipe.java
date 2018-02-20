package main.java.application.web.model.recipes;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recipes")
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	private String name;

	private int caloricity;
	
	@OneToOne(cascade = CascadeType.ALL)
	private DishType dishType;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Description recipeDescription;
	
	public Recipe() {
		super();
	}
	
	public DishType getDishType() {
		return dishType;
	}

	public void setDishType(DishType dishType) {
		this.dishType = dishType;
	}

	public Description getRecipeDescription() {
		return recipeDescription;
	}

	public void setRecipeDescription(Description recipeDescription) {
		this.recipeDescription = recipeDescription;
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

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + ", caloricity=" + caloricity + "]";
	}

}
