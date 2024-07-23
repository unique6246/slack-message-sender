package com.example.assignment.controllerTest;

import com.example.assignment.controller.SlackController;
import com.example.assignment.service.SlackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SlackControllerTest {

    @InjectMocks
    private SlackController slackController;

    @Mock
    private SlackService slackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for valid message sending
    @Test
    void sendSlackMessage_ShouldReturnOk_WhenMessageIsValid() {
        Map<String, String> payload = new HashMap<>();
        payload.put("text", "Hello Slack");

        ResponseEntity<String> response = slackController.sendSlackMessage(payload);

        verify(slackService, times(1)).sendMessage("Hello Slack");
        assertEquals(ResponseEntity.ok("Message sent to Slack"), response);
    }

    // Test for empty message sending
    @Test
    void sendSlackMessage_ShouldReturnBadRequest_WhenMessageIsEmpty() {
        Map<String, String> payload = new HashMap<>();
        payload.put("text", "");

        ResponseEntity<String> response = slackController.sendSlackMessage(payload);

        assertEquals(ResponseEntity.badRequest().body("Message cannot be empty"), response);
    }

    // Test for getting users
    @Test
    void getUsers_ShouldReturnUserList() {
        when(slackService.getUsers()).thenReturn(List.of("User1", "User2"));

        ResponseEntity<List<String>> response = slackController.getUsers();

        assertEquals(ResponseEntity.ok(List.of("User1", "User2")), response);
    }

    // Test for getting channel messages
    @Test
    void getChannelMessages_ShouldReturnMessageList() {
        when(slackService.getChannelMessages()).thenReturn(List.of("Message1", "Message2"));

        ResponseEntity<List<String>> response = slackController.getChannelMessages();

        assertEquals(ResponseEntity.ok(List.of("Message1", "Message2")), response);
    }
}
