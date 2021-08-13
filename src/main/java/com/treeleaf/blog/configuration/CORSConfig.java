package com.treeleaf.blog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; 

@Configuration
public class CORSConfig   {

    private final String ALLOWED_ORIGINS = "http://localhost:3000";

    public CORSConfig(){
        super();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedMethods("GET","POST", "PUT", "DELETE","OPTION")
                        .allowedHeaders("*")
                        .allowedOrigins("*");
            }
        };
    }

  /*  public void addCorsMapping(CorsRegistry registry){
        registry.addMapping("/api/**")
                .allowedMethods("GET","POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowedOrigins("*");
    }*/
}
