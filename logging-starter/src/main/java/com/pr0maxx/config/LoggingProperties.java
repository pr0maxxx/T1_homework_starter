package com.pr0maxx.config;

import com.pr0maxx.logging.LogLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "logging.aspect")
public class LoggingProperties {
    private boolean enabled = true;
    private LogLevel level = LogLevel.INFO;
}
