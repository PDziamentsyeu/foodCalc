package main.java.application.web.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @Email
    @Column(nullable = false) 
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    private User userDetail;

    @ManyToOne
    private Role role;


	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(User userDetail) {
        this.userDetail = userDetail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    public Account(String email, String password, boolean isAdmin) {
        super();
        this.email = email;
        this.password = password;
    }
    

    public Account() {
        super();

    }

    @Override
    public String toString() {
        return "Account {"
                + "id=" + id + ","
                + " email=" + email + ","
                + "pass=" + password+","
                + " role =" + role.getRoleName()+","
                + "}";
    }

}
