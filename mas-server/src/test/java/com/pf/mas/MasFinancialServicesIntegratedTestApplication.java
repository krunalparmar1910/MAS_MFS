package com.pf.mas;

import com.pf.mas.controller.IntegrationTestExtension;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("test")
public class MasFinancialServicesIntegratedTestApplication {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.requiresChannel(channelRequestMatcherRegistry -> channelRequestMatcherRegistry.anyRequest().requiresSecure())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/api/report/**", "/api/corpository-api/**").permitAll());
        return http.build();
    }

    @PreDestroy
    public void onExit() {
        IntegrationTestExtension.close();
    }
}
