package com.example.assignment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// Configuration class for Slack integration
@Configuration
public class SlackConfig {

    // Retrieve the Slack webhook URL and OAuth token from application properties
    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    @Value("${slack.oauth.token}")
    private String slackOauthToken;

    // Bean to create RestTemplate for making HTTP requests
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Bean for Slack webhook URL
    @Bean
    public String slackWebhookUrl() {
        return slackWebhookUrl;
    }

    // Bean for Slack OAuth token
    @Bean
    public String slackOauthToken() {
        return slackOauthToken;
    }
}
