package app.betterhm.backend.controller;

import app.betterhm.backend.model.CalendarElement;
import app.betterhm.backend.services.CalendarJsonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    CalendarJsonService calendarJsonServiceObject = new CalendarJsonService();
    @GetMapping()
    public List<CalendarElement> returnCalendar() {
        return this.calendarJsonServiceObject.getCalendarElementsList();
    }
    @GetMapping("/{id}")
    public CalendarElement getCalendar(@PathVariable String id) {
        return calendarJsonServiceObject.getSingleCalendarElements(id);
    }
}
