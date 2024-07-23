package com.example.assignment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Service class for Slack-related operations
@Service
public class SlackService {

    private final RestTemplate restTemplate;
    private final String slackWebhookUrl;
    private final String slackOauthToken;

    // Constructor-based dependency injection
    public SlackService(RestTemplate restTemplate,
                        @Value("${slack.webhook.url}") String slackWebhookUrl,
                        @Value("${slack.oauth.token}") String slackOauthToken) {
        this.restTemplate = restTemplate;
        this.slackWebhookUrl = slackWebhookUrl;
        this.slackOauthToken = slackOauthToken;
    }

    // Method to send a message to Slack
    public void sendMessage(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Set content type to JSON

        // Create JSON payload
        String payload = "{\"text\":\"" + message + "\"}";

        // Create HTTP entity with payload and headers
        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        // Send POST request to Slack webhook URL
        restTemplate.postForEntity(slackWebhookUrl, entity, String.class);
    }

    // Method to get a list of users from Slack
    public List<String> getUsers() {
        String url = "https://slack.com/api/users.list";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(slackOauthToken); // Set Bearer authentication token
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make GET request to Slack API
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> responseBody = response.getBody();

        // Extract list of users from response
        List<Map<String, Object>> members = (List<Map<String, Object>>) responseBody.get("members");

        return members.stream()
                .map(member -> (String) member.get("real_name")) // Get real names of users
                .collect(Collectors.toList());
    }

    // Method to get messages from a specific Slack channel
    public List<String> getChannelMessages() {
        String url = "https://slack.com/api/conversations.history?channel=C07DQU7EECC"; // Replace with your channel ID
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(slackOauthToken); // Set Bearer authentication token
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make GET request to Slack API
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> responseBody = response.getBody();

        // Extract list of messages from response
        List<Map<String, Object>> messages = (List<Map<String, Object>>) responseBody.get("messages");

        return messages.stream()
                .map(message -> (String) message.get("text")) // Get text of each message
                .collect(Collectors.toList());
    }
}
