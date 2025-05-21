package com.pr0maxx.config;

import com.pr0maxx.logging.LoggingAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(name = "logging.aspect.enabled", havingValue = "true", matchIfMissing = true)
public class LoggingAutoConfiguration {

    @Bean
    public LoggingAspect loggingAspect(Environment environment) {
        return new LoggingAspect(environment);
    }
}
