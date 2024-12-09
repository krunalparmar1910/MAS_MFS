package com.pf.mas.config;

import com.pf.corpository.config.CorpositoryConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.TimeZone;

@Configuration
@Import(value = {CorpositoryConfig.class})
public class MasConfig {
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}