package main.java.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import main.java.application.web.security.jwt.JwtInterceptor;



@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"main"})
public class WebConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
	@Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor());
    }
}
