package com.realtimeEcho.AnalyticsService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "activity_analytics")
@Getter
@Setter
public class ActivityAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String eventId;

    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String action;

    private String page;

    @Column(length = 4000)
    private String metadataJson;

    @Column(nullable = false)
    private Instant eventTime;

    @Column(nullable = false)
    private Instant processedAt;
}
