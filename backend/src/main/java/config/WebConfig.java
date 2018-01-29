package main.java.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"main"})
public class WebConfig extends WebMvcConfigurerAdapter {
    @Value("${application.front.dir}")
    public String frontDirPath;
   
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println(frontDirPath);
        registry.addResourceHandler("/**").addResourceLocations("file:/" + frontDirPath);
    }
}
