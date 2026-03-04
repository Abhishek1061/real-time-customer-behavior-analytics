package com.realtimeEcho.AnalyticsService.repository;

import com.realtimeEcho.AnalyticsService.model.ActivityAnalytics;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivityAnalyticsRepository extends JpaRepository<ActivityAnalytics, Long> {

    Optional<ActivityAnalytics> findByEventId(String eventId);

    List<ActivityAnalytics> findByUsernameOrderByEventTimeDesc(String username);

    List<ActivityAnalytics> findAllByOrderByEventTimeDesc();

    @Query("select a.action as action, count(a) as total from ActivityAnalytics a group by a.action")
    List<ActionCountView> countByAction();

    interface ActionCountView {
        String getAction();
        Long getTotal();
    }
}
