package com.babzip.backend.global.config.web;

import com.babzip.backend.global.oauth.user.KakaoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient kakaoWebClient(KakaoProperties kakaoProperties) {
        return WebClient.builder()
                .baseUrl("https://dapi.kakao.com/v2/local")
                .defaultHeader(HttpHeaders.AUTHORIZATION,
                        "KakaoAK " + kakaoProperties.getClientId())
                .build();
    }
}
