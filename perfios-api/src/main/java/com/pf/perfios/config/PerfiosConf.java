package com.pf.perfios.config;

import com.pf.perfios.utils.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PerfiosConf {

    @Bean
    public ApiClient getApiClient() {
        return new ApiClient();
    }
}
