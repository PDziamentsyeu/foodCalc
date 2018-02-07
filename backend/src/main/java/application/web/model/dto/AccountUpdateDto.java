package main.java.application.web.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Size;

public class AccountUpdateDto {
    @Size(min = 3)
    private final String email;
    @Size(min = 8, max = 32)
    private final String password;
    @Size(min = 8, max = 32)
    private final String newPassword;

    @JsonCreator
    public AccountUpdateDto(@JsonProperty("email") final String email,
                            @JsonProperty("password") final String password,
                            @JsonProperty("newPassword") final String newPassword) {
        this.email = email;
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getEmail() {
        return email;
    }
}
