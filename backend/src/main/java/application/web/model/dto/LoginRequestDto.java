package main.java.application.web.model.dto;

public class LoginRequestDto {
    private final String token;

    public LoginRequestDto(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
