package com.github.slamdev.oldschool.integration;

import ch.qos.logback.access.tomcat.LogbackValve;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty("oldschool.access-logs.enabled")
public class AccessLogsConfiguration {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> accessLogsCustomizer(
            @Value("${oldschool.access-logs.config-file}") String configFile) {
        return factory -> {
            LogbackValve logbackValve = new LogbackValve();
            logbackValve.setQuiet(true);
            logbackValve.setFilename(configFile);
            logbackValve.setAsyncSupported(true);
            factory.addContextValves(logbackValve);
        };
    }
}
