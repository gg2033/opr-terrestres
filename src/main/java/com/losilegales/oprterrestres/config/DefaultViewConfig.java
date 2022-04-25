package com.losilegales.oprterrestres.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc  
public class DefaultViewConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	 registry.addRedirectViewController("/", "/swagger-ui.html");
//        registry.addViewController("/").setViewName("forward:/homepage.html");
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}