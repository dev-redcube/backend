package app.betterhm.backend.v1.models.capacity;

import java.util.List;

public record CapacityConfigRecord(String enum_name, String lrz_subdistrict_id, Integer static_max_clients, List<String> specific_access_points) {}
