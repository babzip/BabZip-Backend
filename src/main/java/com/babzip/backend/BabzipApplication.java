package com.babzip.backend;

import com.babzip.backend.global.oauth.user.KakaoProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(KakaoProperties.class)
public class BabzipApplication {

    public static void main(String[] args) {
        SpringApplication.run(BabzipApplication.class, args);
    }

}
