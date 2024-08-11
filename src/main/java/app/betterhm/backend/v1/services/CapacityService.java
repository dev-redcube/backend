package app.betterhm.backend.v1.services;

import app.betterhm.backend.v1.component.YamlParser;
import app.betterhm.backend.v1.models.capacity.CapacityApiElement;
import app.betterhm.backend.v1.models.capacity.CapacityConfigRecord;
import app.betterhm.backend.v1.models.capacity.LrzJsonAccesspoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

@Controller
public class CapacityService {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CalendarService.class);
    private final List<CapacityConfigRecord> capacityConfigRecordList;
    private List<CapacityApiElement> capacityApiElementList;
    private final String lrzApiUrl;
    public CapacityService(YamlParser yamlParser){
        this.capacityConfigRecordList = yamlParser.getParsedYaml().CapacityElements();
        this.lrzApiUrl = yamlParser.getParsedYaml().LrzUrl();
        updateCapacity();

    }

    /**
     Gets a List of all current Api Elements
     @return a list of CapacityApiElement
     */

    public List<CapacityApiElement> getCapacityApiElementList(){
        return capacityApiElementList;
    }

    /**
     * Finds the CapacityApiElement with the given enum_name
     * @param enum_nameToFind enum_name that is searched for
     * @return found CapacityApiElement or if not found null
     */
    public CapacityApiElement getSingleCapacityElement(String enum_nameToFind){
        return capacityApiElementList.stream()
                .filter(Element -> Element.enum_name().equals(enum_nameToFind))
                .findFirst()
                .orElse(null);
    }

    /**
     * Method to update the Capacity Elements every 5 Minutes
     */
    @Scheduled(cron = "0/5 * * * * *")
    public void updateCapacity(){
        List<CapacityApiElement> newElementList = new ArrayList<>();
        capacityConfigRecordList.forEach(Element -> newElementList.add(getUpdatedCapacityElement(Element)));
        capacityApiElementList = newElementList;
    }

    private CapacityApiElement getUpdatedCapacityElement(CapacityConfigRecord capacityConfigRecord){
        final int clients;
        final Instant timestamp;

        //gets the data from the LRZ API
        String json = getDataFromLRZ(capacityConfigRecord);
        final ArrayList<LrzJsonAccesspoint> allApList = getJsonData(json);
        ArrayList<LrzJsonAccesspoint> filteredApList = filterAccessPoints(capacityConfigRecord, allApList);
        //adds all the clients of the filtered APs to the client count & set timestamp
        if (!filteredApList.isEmpty()) {
            clients = filteredApList.stream().mapToInt(element -> element.getConnectedDevices(1)).sum();
            timestamp = filteredApList.get(0).getDatapointTimestamp(1);
        }else{
            timestamp = Instant.now();
            clients = 0;
        }


        int capacityLevelInPercent = (clients / capacityConfigRecord.static_max_clients());
        //filter out everything over "100%"
        if(capacityLevelInPercent > 1){
            capacityLevelInPercent = 1;
        }

        return new CapacityApiElement(capacityConfigRecord.enum_name(), clients, capacityLevelInPercent, timestamp.toString());
    }

    private static ArrayList<LrzJsonAccesspoint> filterAccessPoints(CapacityConfigRecord capacityConfigRecord, ArrayList<LrzJsonAccesspoint> allApList) {
        ArrayList<LrzJsonAccesspoint> filteredApList = new ArrayList<>();
        if (capacityConfigRecord.specific_access_points() == null) {
            return allApList; // returns all APs if no specific APs are defined
        }
        // filters all relevant APs in the LRZ API response with the specific access points of the Capacity Element
        capacityConfigRecord.specific_access_points().forEach(accessPoint -> filteredApList.addAll(
                allApList.parallelStream()
                         .filter(Element -> Element.target().contains(accessPoint))
                         .toList()));
        return filteredApList; //return only the APs specified in the specific_access_points
    }

    private String getDataFromLRZ(CapacityConfigRecord capacityConfigRecord) {
        //assembles the URL for the LRZ API
        String json = "";
        URL apiUrl;
        try {
            String[] splitUrl = lrzApiUrl.split("\\{\\{subdistrict_id}}", 2); //builds the URL with the sub-district id
            apiUrl = new URL(splitUrl[0] + capacityConfigRecord.lrz_subdistrict_id() + splitUrl[1]);
        } catch (PatternSyntaxException e) {
            logger.error("Could not Split LRZ Api Url Provided by the config", e);
            throw new PatternSyntaxException("Could not Split LRZ Api Url Provided by the config", lrzApiUrl, -1);
        }catch (MalformedURLException e) {
            logger.error("Could not Generate a valid Url for the LRZ Wifi Api", e);
            throw new IllegalStateException("Could not Generate a valid Url for the LRZ Wifi Api", e);
        }
        //gets the data from the LRZ API
        try (InputStream in = apiUrl.openStream()) {
            json = new String(in.readAllBytes()); //gets the data from the LRZ API
        } catch (IOException e) {
            logger.error("Could not get responds from LRZ Api", e);
        }
        return json;
    }

    private ArrayList <LrzJsonAccesspoint> getJsonData(String json) {
        //parses the json response into a list of Access Points
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, new TypeReference<>() {});

        } catch (JsonProcessingException e) {
            logger.error("Could not parse the LRZ API response", e);
            return new ArrayList<>();
        }
    }
}
