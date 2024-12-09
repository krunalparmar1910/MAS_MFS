package com.pf.mas.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@EnableScheduling
@EnableAsync
@Configuration("WebMvcConfiguration")
public class WebMvcConfiguration implements WebMvcConfigurer {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${app.cors.allowed-origins:*}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOrigins(allowedOrigins.split(",")).allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/ui/**").setViewName("forward:/index.html");
        registry.addRedirectViewController("/", "/ui/home");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // overriding this method instead of server.servlet.context-path in application.yml
    // as setting that property changes context of entire application which affects serving static resources
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", HandlerTypePredicate.forAnnotation(RestController.class));
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
        MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter(xmlMapper);
        converters.add(xmlConverter);
    }

    @Bean
    public JPAQueryFactory getJpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
