package main.application.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import main.application.utils.model.GenericEntityWithMeta;

@Entity

public class User  extends GenericEntityWithMeta{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

	private static final long serialVersionUID = 1L;

    private String name;
    
    private String nickName;

    private String email;
    @JsonIgnore
    private String token;
    
    private float weight;
    
    private int height;	

	public User() {
		super();
		
	}

	public User(Long id, String name, String email, float weight, int height, String token, String nickName) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.weight = weight;
		this.height = height;
		this.nickName = nickName;
		this.token = token;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	

	@Override
	public String toString() {
		return "User {id=" + id +
				", name=" + name + 
				", nickName=" + nickName + 
				", email=" + email +
				", token=" + token +
				", weight=" + weight + 
				", height=" + height + "}";
	}
	
	
	
}
