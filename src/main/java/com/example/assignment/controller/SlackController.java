package com.example.assignment.controller;

import com.example.assignment.service.SlackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// REST controller for handling Slack operations
@RestController
public class SlackController {

    private final SlackService slackService;

    // Constructor-based dependency injection for SlackService
    public SlackController(SlackService slackService) {
        this.slackService = slackService;
    }

    // Endpoint to send a message to Slack
    @PostMapping("/sendSlackMessage")
    public ResponseEntity<String> sendSlackMessage(@RequestBody Map<String, String> payload) {
        String message = payload.get("text");

        // Check if the message is empty
        if (message == null || message.isEmpty()) {
            return ResponseEntity.badRequest().body("Message cannot be empty");
        }

        // Send the message using SlackService
        slackService.sendMessage(message);
        return ResponseEntity.ok("Message sent to Slack");
    }

    // Endpoint to retrieve a list of users from Slack
    @GetMapping("/users")
    public ResponseEntity<List<String>> getUsers() {
        return ResponseEntity.ok(slackService.getUsers());
    }

    // Endpoint to retrieve messages from a specific Slack channel
    @GetMapping("/messages")
    public ResponseEntity<List<String>> getChannelMessages() {
        return ResponseEntity.ok(slackService.getChannelMessages());
    }
}
