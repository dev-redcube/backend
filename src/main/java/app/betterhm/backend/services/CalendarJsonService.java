package app.betterhm.backend.services;
import app.betterhm.backend.model.CalendarElement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarJsonService {
    private final List<CalendarElement> calendarElementsList;

    public CalendarJsonService() {
        this.calendarElementsList = calendarJsonFactory();
        
        if (this.calendarElementsList == null) {
            throw new IllegalStateException("Calendar elements list is empty");
        }

         
    }

    public List<CalendarElement> getCalendarElementsList(){
        return this.calendarElementsList;
    }

    public CalendarElement getSingleCalendarElements(String calendarElementID) {
        return calendarElementsList.stream()
                .filter(Element -> Element.ID().equals(calendarElementID))
                .findFirst()
                .orElse(null);
    }

    private List<CalendarElement> calendarJsonFactory() {
        try {
            List<CalendarElement> calendarElementsList = List.of(null); //todo Implement yaml parser
        } catch (Exception e) {
            throw new IllegalStateException("could not parse the yml file", e);
        }
        return calendarElementsList;}
}
