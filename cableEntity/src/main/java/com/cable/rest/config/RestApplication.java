
package com.cable.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EntityScan(value = { "com.cable.rest.model" })
@EnableJpaRepositories(value = { "com.cable.rest.repository" })
@ComponentScan("com.cable.rest")
@PropertySource("classpath:application.properties")
@EnableJpaAuditing
@EnableCaching
// This class is the entry point of the application.
public class RestApplication {

    @Autowired
    private Environment env;

    @Bean
    public CacheManager getEhCacheManager() {
        return new EhCacheCacheManager(getEhCacheFactory().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean getEhCacheFactory() {
        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        factoryBean.setShared(true);
        return factoryBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);

    }

}