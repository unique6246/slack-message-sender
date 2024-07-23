package com.example.assignment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SlackConfig {

    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    @Value("${slack.oauth.token}")
    private String slackOauthToken;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public String slackWebhookUrl() {
        return slackWebhookUrl;
    }

    @Bean
    public String slackOauthToken() {
        return slackOauthToken;
    }
}
