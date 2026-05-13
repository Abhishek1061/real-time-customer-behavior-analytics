package com.realtimeEcho.ActivityService.controller;

import com.realtimeEcho.ActivityService.dto.ActivityEvent;
import com.realtimeEcho.ActivityService.dto.ActivityRequest;
import com.realtimeEcho.ActivityService.dto.PublishResponse;
import com.realtimeEcho.ActivityService.service.ActivityProducerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityProducerService producerService;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("UP");
    }

    @PostMapping
    public ResponseEntity<PublishResponse> publish(@Valid @RequestBody ActivityRequest request) {
        ActivityEvent event = producerService.publish(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new PublishResponse("Activity published", event.getEventId()));
    }
}
