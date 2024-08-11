package app.betterhm.backend.v1.models;

import app.betterhm.backend.v1.models.capacity.CapacityConfigRecord;

import java.util.List;

/**
 * This Record is a Template for the Yaml File
 */
public record YamlRecord(List<CalendarElement> Calendars, List<CapacityConfigRecord> CapacityElements, String LrzUrl) {
    public YamlRecord {
        if (Calendars == null || CapacityElements == null || LrzUrl == null) {
            throw new IllegalArgumentException("All fields must be set, config.yml is invalid.");
        }
    }

}
