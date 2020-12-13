package com.ctem;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * @author Arvind Maurya
 *
 */
@SpringBootApplication
@EntityScan(basePackageClasses = { 
		CtemBackendApplication.class,
		Jsr310JpaConverters.class 
})
public class CtemBackendApplication extends SpringBootServletInitializer  {
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CtemBackendApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(CtemBackendApplication.class, args);
	}

	@SuppressWarnings("deprecation")
	@Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurerAdapter() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")
	                        .allowedMethods("*")
	                       // .allowedOrigins("http://18.236.81.121");
	                        .allowedOrigins("http://localhost:3000","http://3.6.207.6:8081","http://localhost:8080","http://192.168.0.6:8080","http://3.6.207.6:3000");
	            }
	        };
	    }
}
