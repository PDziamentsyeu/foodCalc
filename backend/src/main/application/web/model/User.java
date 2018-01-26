package main.application.web.model;

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

    private String name;
    
    private String nickName;
	
    private float weight;
    
    private int height;	
    
    @OneToOne(mappedBy = "userDetail")
    private Account account;

	public User() {
		super();
		
	}

	public User(Long id, String name, String email, float weight, int height, String token, String nickName) {
		super();
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.height = height;
		this.nickName = nickName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "User {id=" + id +
				", name=" + name + 
				", nickName=" + nickName + 
				", weight=" + weight + 
				", height=" + height + "}";
	}
	
	
	
}
