package com.realtimeEcho.ActivityService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PublishResponse {
    private final String message;
    private final String eventId;
}
