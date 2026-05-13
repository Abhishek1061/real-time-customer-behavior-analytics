package com.realtimeEcho.ActivityService.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityRequest {
    private Long userId;

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "action is required")
    private String action;

    private String page;
    private Map<String, Object> metadata;
    private Instant timestamp;
}
