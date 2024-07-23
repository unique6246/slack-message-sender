package com.example.assignment.controller;

import com.example.assignment.service.SlackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SlackController {

    private final SlackService slackService;

    public SlackController(SlackService slackService) {
        this.slackService = slackService;
    }

    @PostMapping("/sendSlackMessage")
    public ResponseEntity<String> sendSlackMessage(@RequestBody Map<String, String> payload) {
        String message = payload.get("text");
        if (message == null || message.isEmpty()) {
            return ResponseEntity.badRequest().body("Message cannot be empty");
        }

        slackService.sendMessage(message);
        return ResponseEntity.ok("Message sent to Slack");
    }

    @GetMapping("/users")
    public ResponseEntity<List<String>> getUsers() {
        return ResponseEntity.ok(slackService.getUsers());
    }

    @GetMapping("/messages")
    public ResponseEntity<List<String>> getChannelMessages() {
        return ResponseEntity.ok(slackService.getChannelMessages());
    }
}
