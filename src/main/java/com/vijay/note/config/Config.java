package com.vijay.note.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public AuditorConfig auditAware(){
        return new AuditorConfig();
    }
}
