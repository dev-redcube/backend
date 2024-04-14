package app.betterhm.backend.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CalendarIcalService {

    public CalendarIcalService() {
        this.updateIcal();
    }

    @Scheduled(cron = "0 4 * * *")
    private void updateIcal(){
        //todo implement function to download newest ical file
    }
}
