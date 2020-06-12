package com.codecool.movieorganizer.config;

import com.codecool.movieorganizer.controller.AuthController;
import com.codecool.movieorganizer.controller.MovieController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

    @Bean
    public Logger authControllerLogger() {
        return LoggerFactory.getLogger(AuthController.class);
    }

    @Bean
    public Logger movieControllerLogger() {
        return LoggerFactory.getLogger(MovieController.class);
    }
}
