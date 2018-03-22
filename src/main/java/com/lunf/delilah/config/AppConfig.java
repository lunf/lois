package com.lunf.delilah.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.bus.EventBus;


@Configuration
@EnableAutoConfiguration
@ComponentScan
public class AppConfig {

    /**
     * Environment for EventBus
     * @return
     */
//    @Bean
//    Environment env() {
//        return Environment.initializeIfEmpty()
//                .assignErrorJournal();
//    }

//    @Bean
//    EventBus createEventBus(Environment env) {
//      //  return EventBus.create(env, Environment.THREAD_POOL);
//    }
}
