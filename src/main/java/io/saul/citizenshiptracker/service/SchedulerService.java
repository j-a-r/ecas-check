package io.saul.citizenshiptracker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SchedulerService {

    private final ECASTrackerService ecasTrackerService;
    private final static Logger log = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    public SchedulerService(ECASTrackerService ecasTrackerService) {
        this.ecasTrackerService = ecasTrackerService;
    }

    @PostConstruct
    @Scheduled(cron = "0 0/20 9,10,11,12,13,14,15,16,17,18,19,20,21 ? * MON,TUE,WED,THU,FRI")
    public void scheduledTask(){
        String resultingURL = ecasTrackerService.fire();
        if(resultingURL == null) {
            log.info("No updates.");
        }
        else{
            log.info("DIFFERENT URL RECEIVED: " + resultingURL);
            System.exit(0);
        }
    }
}
