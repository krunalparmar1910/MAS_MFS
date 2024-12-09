package com.pf.karza.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(BigDecimal.class, new CustomBigDecimalDeserializer() {
        });
        objectMapper.registerModule(simpleModule);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
