package main.java.application.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accountRecipes")
public class AccountRecipes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
}
