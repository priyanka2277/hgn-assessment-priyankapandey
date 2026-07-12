package com.example.hgn.Alert;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class AlertEscalationScheduler {
    private final AlertRepository alertRepository;
    @Value("${alert.escalation.threshold-minutes:10}")
    private long thresholdMinutes;

    public AlertEscalationScheduler(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void escalateAlerts() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(thresholdMinutes);
        int count = alertRepository.escalateStaleAlerts(cutoff);
    }


}
