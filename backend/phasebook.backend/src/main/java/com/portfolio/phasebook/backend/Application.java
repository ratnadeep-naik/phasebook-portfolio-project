package com.portfolio.phasebook.backend;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	 
	@Bean 
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://phasebookfrontend.s3-website.us-east-2.amazonaws.com", "http://phasebook-frontend.s3-website.us-east-2.amazonaws.com"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
     
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		 return new WebMvcConfigurerAdapter() {
//			 @Override
//			 public void addCorsMappings(CorsRegistry registry) {
//				 registry.addMapping("/**")
//				 .allowedMethods("*")
//				 .allowedOrigins("http://localhost:4200")
//				 .allowCredentials(true)
//				 ;
//				 
////				 registry.addMapping("/**")
////				 .allowedMethods("GET", "POST", "PUT", "DELETE")
////				 .allowedOrigins("http://localhost:4200");
////				 .allowedOrigins("*");
////                 .allowedHeaders("*");
//				 
//
//			 }
//		 };
//	}


}