package main.java.application.web.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginDto {
    @NotBlank
    private final String email;
    @NotBlank
    @Size(min = 8, max = 32)
    private final String password;

    @JsonCreator
    public LoginDto(@JsonProperty("email") final String email,
                    @JsonProperty("password") final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
