package com.realtimeEcho.AnalyticsService.service;

import com.realtimeEcho.AnalyticsService.dto.ActivityEvent;
import com.realtimeEcho.AnalyticsService.model.ActivityAnalytics;
import com.realtimeEcho.AnalyticsService.repository.ActivityAnalyticsRepository;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ActivityAnalyticsRepository analyticsRepository;

    @Transactional
    public void saveEvent(ActivityEvent event) {
        if (event.getEventId() == null || event.getEventId().isBlank()) {
            return;
        }
        if (analyticsRepository.findByEventId(event.getEventId()).isPresent()) {
            return;
        }

        ActivityAnalytics row = new ActivityAnalytics();
        row.setEventId(event.getEventId());
        row.setUserId(event.getUserId());
        row.setUsername(event.getUsername() == null ? "unknown" : event.getUsername());
        row.setAction(event.getAction() == null ? "UNKNOWN" : event.getAction());
        row.setPage(event.getPage());
        row.setMetadataJson(event.getMetadataJson());
        row.setEventTime(event.getEventTime() == null ? Instant.now() : event.getEventTime());
        row.setProcessedAt(Instant.now());
        analyticsRepository.save(row);
    }

    @Transactional(readOnly = true)
    public List<ActivityAnalytics> findAll() {
        return analyticsRepository.findAllByOrderByEventTimeDesc();
    }

    @Transactional(readOnly = true)
    public List<ActivityAnalytics> findByUsername(String username) {
        return analyticsRepository.findByUsernameOrderByEventTimeDesc(username);
    }

    @Transactional(readOnly = true)
    public Map<String, Long> actionSummary() {
        Map<String, Long> result = new LinkedHashMap<>();
        analyticsRepository.countByAction().forEach(v -> result.put(v.getAction(), v.getTotal()));
        return result;
    }
}
