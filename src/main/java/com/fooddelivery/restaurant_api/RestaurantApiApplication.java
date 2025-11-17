package com.fooddelivery.restaurant_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class RestaurantApiApplication {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantApiApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RestaurantApiApplication.class);
        Environment env = app.run(args).getEnvironment();

        logger.info("\n----------------------------------------------------------\n" +
                        "Application '{}' is running!\n" +
                        "Access URLs:\n" +
                        "Local: \t\thttp://localhost:{}{}\n" +
                        "Profile(s): \t{}\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path"),
                env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "default"
        );
    }
}