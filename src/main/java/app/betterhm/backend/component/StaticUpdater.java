package app.betterhm.backend.component;

import app.betterhm.backend.models.YamlRecord;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * This class is responsible for updating the static files in the project
 */
@Component
public class StaticUpdater {
    private final YamlRecord yaml;

    public StaticUpdater(YamlParser yamlParser) {
        this.yaml = yamlParser.getParsedYaml();
    }

    /**
     * Downloads the calendar files from the source URLs and saves them in the static folder
     */
    @PostConstruct
    @Scheduled(cron = "0 4 * * *")
    private void updateCalendar(){
        yaml.Calendars().stream().filter(element -> element.SourceURL().isPresent()).forEach(element ->
                downloadFile(element.SourceURL().get(), "src/main/resources/static/calendar/" + element.ID() + ".ics"));
    }

    /**
     * Downloads the file from the given URL and saves it in the given path
     * @param url  Web URL of the file
     * @param path Path to save the file to
     */
    private void downloadFile(String url, String path) {
        URL website;
        try {
            website = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid URL", e);
        }
        try (InputStream in = website.openStream()) {
            Files.copy(in, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file", e);
        }
    }
}
