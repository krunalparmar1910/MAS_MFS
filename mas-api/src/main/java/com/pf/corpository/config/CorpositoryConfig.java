package com.pf.corpository.config;

import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EntityScan(basePackages = {"com.pf.corpository"})
@ComponentScan(basePackages = {"com.pf.corpository"})
public class CorpositoryConfig {
    private static final int MAX_TIMEOUT_MINUTES = 60;

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(MAX_TIMEOUT_MINUTES, TimeUnit.MINUTES)
                .connectTimeout(MAX_TIMEOUT_MINUTES, TimeUnit.MINUTES)
                .readTimeout(MAX_TIMEOUT_MINUTES, TimeUnit.MINUTES)
                .writeTimeout(MAX_TIMEOUT_MINUTES, TimeUnit.MINUTES)
                .build();
    }
}
