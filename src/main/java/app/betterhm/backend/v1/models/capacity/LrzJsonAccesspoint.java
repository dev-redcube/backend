package app.betterhm.backend.v1.models.capacity;

import java.time.Instant;
import java.util.ArrayList;

public record LrzJsonAccesspoint(String target, ArrayList<ArrayList<Integer>> datapoints) {
    /**
     * @return Connected devices from index datapoint
     */
    public Integer getConnectedDevices(int index) {
        Integer returnvalue = datapoints.get(index).getFirst();
        return returnvalue == null ? 0: returnvalue;
    }
    /**
    @return Unix Timestamp of datapoint from index datapoint
     */
    public Instant getDatapointTimestamp(int index) {
        return Instant.ofEpochSecond(datapoints.get(index).get(1));
    }
}
