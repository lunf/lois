package com.lunf.delilah.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value("classpath:delilah-lunf-firebase-adminsdk.json")
    private Resource firebaseServiceConfig;

    @Value("${firebase.database-url}")
    private String firebaseUrl;

    @Bean
    public FirebaseApp firebaseApp() throws IOException{
        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(firebaseServiceConfig.getInputStream()))
                .setDatabaseUrl(firebaseUrl)
                .build();

        return FirebaseApp.initializeApp(firebaseOptions);
    }
}
