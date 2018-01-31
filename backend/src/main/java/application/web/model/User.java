package main.java.application.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class User{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    private String firstName;
    
    private String lastName;
	
    private float weight;
    
    private String birthday;
    
    private int height;	
    
    @OneToOne(mappedBy = "userDetail")
    private Account account;

	public User() {
		super();
		
	}

	public User(Long id, String name, String email, float weight, int height, String token, String nickName) {
		super();
		this.id = id;
		this.firstName = name;
		this.weight = weight;
		this.height = height;
		this.lastName = nickName;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "User {id=" + id +
				", firstName=" + firstName + 
				", lastName=" + lastName + 
				", birthday=" + birthday +
				", weight=" + weight + 
				", height=" + height + "}";
	}
	
	
	
}
