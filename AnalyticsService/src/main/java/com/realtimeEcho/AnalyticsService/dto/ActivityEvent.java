package com.realtimeEcho.AnalyticsService.dto;

import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActivityEvent {
    private String eventId;
    private Long userId;
    private String username;
    private String action;
    private String page;
    private String metadataJson;
    private Instant eventTime;
}
