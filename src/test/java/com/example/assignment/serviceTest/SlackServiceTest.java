package com.example.assignment.serviceTest;

import com.example.assignment.service.SlackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class SlackServiceTest {

    @InjectMocks
    private SlackService slackService;

    @Mock
    private RestTemplate restTemplate;

    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    @Value("${slack.oauth.token}")
    private String slackOauthToken;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessage_ShouldSendMessageToSlack() {
        String message = "Hello Slack";
        String payload = "{\"text\":\"" + message + "\"}";
        when(restTemplate.postForEntity(eq(slackWebhookUrl), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok("Message sent"));

        slackService.sendMessage(message);

    }

    @Test
    void getUsers_ShouldReturnUserList() {
        String url = "https://slack.com/api/users.list";
        Map<String, Object> responseBody = Map.of(
                "members", List.of(
                        Map.of("real_name", "User1"),
                        Map.of("real_name", "User2")
                )
        );
        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity<>(responseBody, HttpStatus.OK));

        List<String> users = slackService.getUsers();

        assertEquals(List.of("User1", "User2"), users);
    }

    @Test
    void getChannelMessages_ShouldReturnMessageList() {
        String url = "https://slack.com/api/conversations.history?channel=C07DQU7EECC";
        Map<String, Object> responseBody = Map.of(
                "messages", List.of(
                        Map.of("text", "Message1"),
                        Map.of("text", "Message2")
                )
        );
        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity<>(responseBody, HttpStatus.OK));

        List<String> messages = slackService.getChannelMessages();

        assertEquals(List.of("Message1", "Message2"), messages);
    }
}
