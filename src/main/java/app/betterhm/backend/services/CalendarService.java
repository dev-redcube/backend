package app.betterhm.backend.services;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {
    private String CalendarIcal;

    public CalendarService() {
        this.updateCalendar();
    }

    @Scheduled(cron = "0 4 * * *")
    private void updateCalendar(){
        String newCalendarIcal = null;
            //todo get Calendar elements
        CalendarIcal = newCalendarIcal;
    }
    public String getCalendar(){
        return CalendarIcal;
    }
}
