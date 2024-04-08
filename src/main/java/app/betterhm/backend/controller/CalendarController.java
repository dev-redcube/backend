package app.betterhm.backend.controller;

import app.betterhm.backend.services.CalendarService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calendars")
public class CalendarController {

    CalendarService CalendarServiceObject = new CalendarService();
    public String returnCalendar() {
        return null;
    }
}
