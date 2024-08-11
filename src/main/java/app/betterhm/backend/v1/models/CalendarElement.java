package app.betterhm.backend.v1.models;

import java.util.Optional;

/**
 * This Record is a Template for the calendar element
 */
public record CalendarElement(String ID, String Name, String Url, Optional<String> SourceURL, Optional<String> Description) {}
