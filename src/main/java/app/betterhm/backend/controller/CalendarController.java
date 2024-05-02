package app.betterhm.backend.controller;

import app.betterhm.backend.models.CalendarElement;
import app.betterhm.backend.services.CalendarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class is responsible for handling the calendar requests
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarService calendarServiceObject;

    public CalendarController(CalendarService calendarServiceObject) {
        this.calendarServiceObject = calendarServiceObject;
    }
    /**
     * Returns the list of calendar elements
     * @return Json list of calendar elements
     */
    @GetMapping()
    public List<CalendarElement> returnCalendar() {
        return this.calendarServiceObject.getCalendarElementsList();
    }

    /**
     * Returns the calendar element with the given ID
     * @param id UUID of the calendar element
     * @return Json of the calendar element
     */
    @GetMapping("/{id}")
    public CalendarElement getCalendar(@PathVariable String id) {
        return calendarServiceObject.getSingleCalendarElements(id);
    }
}
