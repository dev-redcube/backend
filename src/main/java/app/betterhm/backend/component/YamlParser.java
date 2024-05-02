package app.betterhm.backend.component;
import app.betterhm.backend.models.YamlRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is responsible for parsing the yaml config file
 */
@Repository
public class YamlParser {
    private final YamlRecord parsedYaml;

    /**
     * Constructor that reads the yaml file and parses it
     * @throws IOException if error with reading/parsing the file
     */
    public YamlParser() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        try {
            parsedYaml = mapper.readValue(new File("src/main/resources/yaml/config.yml"), YamlRecord.class);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Yaml file not found.");
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Returns the parsed yaml record
     * @return YamlRecord
     */
    public YamlRecord getParsedYaml() {
        return parsedYaml;
    }
}
