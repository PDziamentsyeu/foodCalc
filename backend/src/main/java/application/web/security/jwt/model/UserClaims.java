package main.java.application.web.security.jwt.model;

public class UserClaims {
    private long id;
    private String role;
    private String username;

    public UserClaims(final long id, final String role, final String username) {
        this.role = role;
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "UserClaims{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
