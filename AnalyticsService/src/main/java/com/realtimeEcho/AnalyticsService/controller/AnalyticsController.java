package com.realtimeEcho.AnalyticsService.controller;

import com.realtimeEcho.AnalyticsService.model.ActivityAnalytics;
import com.realtimeEcho.AnalyticsService.service.AnalyticsService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }

    @GetMapping
    public ResponseEntity<List<ActivityAnalytics>> getAll() {
        return ResponseEntity.ok(analyticsService.findAll());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ActivityAnalytics>> byUsername(@PathVariable String username) {
        return ResponseEntity.ok(analyticsService.findByUsername(username));
    }

    @GetMapping("/summary/actions")
    public ResponseEntity<Map<String, Long>> actionSummary() {
        return ResponseEntity.ok(analyticsService.actionSummary());
    }
}
