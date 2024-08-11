package app.betterhm.backend.v1.controller;

import app.betterhm.backend.v1.models.capacity.CapacityApiElement;
import app.betterhm.backend.v1.services.CapacityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/capacity")
public class CapacityController {

    private final CapacityService capacityService;

    public CapacityController(CapacityService capacityService) {
        this.capacityService = capacityService;
    }

    @GetMapping()
    public List<CapacityApiElement> getCapacityElementList(){
        return capacityService.getCapacityApiElementList();
    }

    @GetMapping("/{id}")
    public CapacityApiElement getSingleCapacityElement(@PathVariable String id){
        return capacityService.getSingleCapacityElement(id);
    }
}
