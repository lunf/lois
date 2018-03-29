package com.lunf.delilah.config;

import com.lunf.delilah.business.MessageManager;
import com.lunf.delilah.business.MessageWorker;
import com.lunf.delilah.domain.DisruptorPoolExceptionHandler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
@ComponentScan
public class AppConfig {

    @Bean
    public DisruptorPoolExceptionHandler disruptorPoolExceptionHandler() {
        return new DisruptorPoolExceptionHandler();
    }

    @Bean
    public MessageManager messageManager() {
        return new MessageManager();
    }

    @Bean
    public MessageWorker messageProcessor() {
        return new MessageWorker();
    }
}
