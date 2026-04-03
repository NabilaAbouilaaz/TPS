package com.ecommerce.productservice.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorInfoConfig {

    @Bean
    InfoContributor appInfoContributor(
            @Value("${spring.application.name}") String applicationName,
            @Value("${info.app.description}") String description,
            @Value("${info.app.version}") String version) {
        return builder -> builder.withDetail("app", Map.of(
                "name", applicationName,
                "description", description,
                "version", version
        ));
    }
}
