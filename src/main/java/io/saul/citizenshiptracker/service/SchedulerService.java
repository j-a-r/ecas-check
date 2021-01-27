package io.saul.citizenshiptracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class SchedulerService {

    private final ECASTrackerService ecasTrackerService;

    @Autowired
    public SchedulerService(ECASTrackerService ecasTrackerService) {
        this.ecasTrackerService = ecasTrackerService;
    }

    @PostConstruct
    @Scheduled(cron = "0 0/20 9,10,11,12,13,14,15,16,17,18,19,20,21 ? * MON,TUE,WED,THU,FRI")
    public void scheduledTask(){
        String resultingURL = ecasTrackerService.fire();
        if(resultingURL == null) {
        }
    }
}
