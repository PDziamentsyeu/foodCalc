package main.java.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import main.java.config.WebConfig;

@SpringBootApplication
@Import(value = {
        WebConfig.class,
})
public class Application{

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

}
