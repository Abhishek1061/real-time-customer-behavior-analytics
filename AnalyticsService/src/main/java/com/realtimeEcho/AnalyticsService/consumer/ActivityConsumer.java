package com.realtimeEcho.AnalyticsService.consumer;

import com.realtimeEcho.AnalyticsService.dto.ActivityEvent;
import com.realtimeEcho.AnalyticsService.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ActivityConsumer {

    private final AnalyticsService analyticsService;

    @KafkaListener(
            topics = "${app.kafka.topic.customer-activity}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ActivityEvent event) {
        analyticsService.saveEvent(event);
        log.info("Processed activity event: {}", event.getEventId());
    }
}
