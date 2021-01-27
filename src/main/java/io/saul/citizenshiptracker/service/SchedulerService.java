package io.saul.citizenshiptracker.service;

import io.saul.citizenshiptracker.service.notification.GMailNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class SchedulerService {

    private final ECASTrackerService ecasTrackerService;
    private final GMailNotificationService gMailNotificationService;
    private final static Logger log = LoggerFactory.getLogger(SchedulerService.class);
    private final String destination;

    @Autowired
    public SchedulerService(ECASTrackerService ecasTrackerService, GMailNotificationService gMailNotificationService, @Value("${gmail.sender}")String destination) {
        this.ecasTrackerService = ecasTrackerService;
        this.gMailNotificationService = gMailNotificationService;
        this.destination = destination;
    }

    @PostConstruct
    @Scheduled(cron = "0 0/20 9,10,11,12,13,14,15,16,17,18,19,20,21 ? * MON,TUE,WED,THU,FRI")
    public void scheduledTask(){
        String resultingURL = ecasTrackerService.fire();
        if(resultingURL == null) {
            log.info("No updates.");
            try {
                gMailNotificationService.send("martin@saul.io", "Nope :(", "Fission mailed");
            } catch (IOException | MessagingException e) {
                log.error("Failed to send email due to exception.", e);
            }
        }
        else{
            log.info("DIFFERENT URL RECEIVED: " + resultingURL);
            try {
                gMailNotificationService.send("martin@saul.io", "ｷﾀ━━━━ヽ(ﾟ∀ﾟ )ﾉ━━━━!!!", "ヽ(∀ﾟ )人(ﾟ∀ﾟ)人( ﾟ∀)ノ\nGot: " + resultingURL);
            } catch (IOException | MessagingException e) {
                log.error("Failed to send email due to exception.", e);
            }
        }
    }
}
