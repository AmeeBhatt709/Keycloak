package com.example.Keyclock.config;

import lombok.NonNull;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


//This class is used to configure cross origin resource sharing for spring application
@Configuration
public class CrossOriginFilter implements WebMvcConfigurer {


    //cors is a security feature implemented by web browser  to restrict web pages from making requests  to
    //different origin  than the one that served the original page.

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }

    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        ApplicationConversionService.configure(registry);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}


//        @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("*")
////                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH","");
//
//                .allowedMethods("*");
//    }
//
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        ApplicationConversionService.configure(registry);
//    }
//}
