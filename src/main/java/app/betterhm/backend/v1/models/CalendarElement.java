package app.betterhm.backend.v1.models;

import java.util.Optional;

/**
 * This Record is a Template for the calendar element
 */
public record CalendarElement(
        String id,
        String name,
        String url,
        Optional<String> sourceURL,
        Optional<String> description
) {
}
