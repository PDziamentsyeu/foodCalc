package main.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.application.dto.Home;

@RestController
@RequestMapping("/foodCalc")
public class HomeController {

    @GetMapping
    public Home home() {
        return new Home("hello");
    }

}
