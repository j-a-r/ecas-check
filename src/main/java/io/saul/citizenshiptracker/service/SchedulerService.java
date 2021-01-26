package io.saul.citizenshiptracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    private final ECASTrackerService ecasTrackerService;

    @Autowired
    public SchedulerService(ECASTrackerService ecasTrackerService) {
        this.ecasTrackerService = ecasTrackerService;
    }

    @Scheduled(fixedDelay = 30 * 1000 * 60)
    public void scheduledTask(){
        String resultingURL = ecasTrackerService.fire();
    }
}
