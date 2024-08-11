package app.betterhm.backend.v1.services;
import app.betterhm.backend.v1.component.YamlParser;
import app.betterhm.backend.v1.models.CalendarElement;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * This class is responsible for providing the calendar service to the controllers
 */
@Service
public class CalendarService {
    private final List<CalendarElement> calendarElementsList;
    public CalendarService(YamlParser yamlParser) {
        this.calendarElementsList = yamlParser.getParsedYaml().Calendars();
    }

    /**
     * Returns the list of calendar elements
     * @return List of calendar elements
     */
    public List<CalendarElement> getCalendarElementsList(){
        return this.calendarElementsList;
    }

    /**
     * Returns the calendar element with the given ID
     * @param calendarElementID UUID of the calendar element
     * @return CalendarElement
     */
    public CalendarElement getSingleCalendarElements(String calendarElementID) {
        return calendarElementsList.stream()
                .filter(Element -> Element.ID().equals(calendarElementID))
                .findFirst()
                .orElse(null);
    }
}
