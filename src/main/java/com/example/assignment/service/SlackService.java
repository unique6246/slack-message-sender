package com.example.assignment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SlackService {

    private final RestTemplate restTemplate;
    private final String slackWebhookUrl;
    private final String slackOauthToken;

    public SlackService(RestTemplate restTemplate,
                        @Value("${slack.webhook.url}") String slackWebhookUrl,
                        @Value("${slack.oauth.token}") String slackOauthToken) {
        this.restTemplate = restTemplate;
        this.slackWebhookUrl = slackWebhookUrl;
        this.slackOauthToken = slackOauthToken;
    }

    public void sendMessage(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String payload = "{\"text\":\"" + message + "\"}";

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        restTemplate.postForEntity(slackWebhookUrl, entity, String.class);
    }

    public List<String> getUsers() {
        String url = "https://slack.com/api/users.list";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(slackOauthToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> responseBody = response.getBody();

        List<Map<String, Object>> members = (List<Map<String, Object>>) responseBody.get("members");

        return members.stream()
                .map(member -> (String) member.get("real_name"))
                .collect(Collectors.toList());

    }

    public List<String> getChannelMessages() {
        String url = "https://slack.com/api/conversations.history?channel=C07DQU7EECC";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(slackOauthToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> responseBody = response.getBody();

        List<Map<String, Object>> messages = (List<Map<String, Object>>) responseBody.get("messages");

        return messages.stream()
                .map(message -> (String) message.get("text"))
                .collect(Collectors.toList());
    }
}
