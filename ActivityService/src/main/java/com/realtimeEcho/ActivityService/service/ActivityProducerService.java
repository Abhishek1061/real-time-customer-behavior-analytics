package com.realtimeEcho.ActivityService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimeEcho.ActivityService.dto.ActivityEvent;
import com.realtimeEcho.ActivityService.dto.ActivityRequest;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityProducerService {

    @Value("${app.kafka.topic.customer-activity}")
    private String topic;

    private final KafkaTemplate<String, ActivityEvent> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public ActivityEvent publish(ActivityRequest request) {
        ActivityEvent event = ActivityEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .userId(request.getUserId())
                .username(request.getUsername())
                .action(request.getAction())
                .page(request.getPage())
                .metadataJson(toJson(request))
                .eventTime(request.getTimestamp() == null ? Instant.now() : request.getTimestamp())
                .build();

        String key = request.getUsername();
        kafkaTemplate.send(topic, key, event);
        return event;
    }

    private String toJson(ActivityRequest request) {
        if (request.getMetadata() == null) {
            return "{}";
        }
        try {
            return objectMapper.writeValueAsString(request.getMetadata());
        } catch (JsonProcessingException ex) {
            return "{\"error\":\"metadata serialization failed\"}";
        }
    }
}
