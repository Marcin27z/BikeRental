package BikeRental.BikeRentalREST.station;

import BikeRental.BikeRentalREST.CustomMessage;
import BikeRental.BikeRentalREST.station.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StationController {

    private StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/api/stations/getStations")
    public List<Station> getStations() {
        return stationService.getAllStations();
    }

    @PostMapping("/admin/stations/addStation")
    public CustomMessage addStation(@Valid @RequestBody Station station) {
        if (stationService.addStation(station)) {
            return new CustomMessage(1, "Station added.");
        }
        return new CustomMessage(0, "Station not added. Probably station already exists.");
    }

    @PutMapping("/admin/stations/deleteStation")
    public CustomMessage deleteStation(@RequestParam Long stationId) {
        if (stationService.deleteStation(stationId)) {
            return new CustomMessage(1, "Station deleted.");
        }
        return new CustomMessage(0, "Station not deleted. Probably station doesn't exist.");
    }
}
