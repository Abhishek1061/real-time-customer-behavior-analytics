package com.realtimeEcho.ActivityService.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityEvent {
    private String eventId;
    private Long userId;
    private String username;
    private String action;
    private String page;
    private String metadataJson;
    private Instant eventTime;
}
