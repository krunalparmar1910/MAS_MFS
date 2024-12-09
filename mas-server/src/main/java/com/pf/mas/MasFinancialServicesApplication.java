package com.pf.mas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.pf.mas", "com.pf.perfios", "com.pf.karza", "com.pf.common"})
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.pf.perfios.*", "com.pf.corpository.*", "com.pf.karza.*"})
@EntityScan(basePackages = {"com.pf.mas.model.*", "com.pf.perfios.model.*", "com.pf.karza.model.*", "com.pf.common.model.*"})
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableScheduling
@EnableRetry
public class MasFinancialServicesApplication {
    private static final String AVAILABLE_PROCESSORS_COUNT_PROPERTY = "availableProcessorsCount";

    public static void main(String[] args) {
        SpringApplication.run(MasFinancialServicesApplication.class, args);
    }

    @Bean
    public AsyncTaskExecutor taskExecutor() {
        int availableProcessors = getAvailableProcessors();
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize((availableProcessors + 1) * 2);
        taskExecutor.setMaxPoolSize((availableProcessors + 1) * 4);
        taskExecutor.setQueueCapacity(1000);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix("MAS-AsyncExecutor-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }


    private int getAvailableProcessors() {
        try {
            return Integer.parseInt(System.getProperty(AVAILABLE_PROCESSORS_COUNT_PROPERTY));
        } catch (NumberFormatException e) {
            return Runtime.getRuntime().availableProcessors();
        }
    }
}
