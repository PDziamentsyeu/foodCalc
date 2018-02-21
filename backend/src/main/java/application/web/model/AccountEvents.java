package main.java.application.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accountEvents")
public class AccountEvents {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
}
