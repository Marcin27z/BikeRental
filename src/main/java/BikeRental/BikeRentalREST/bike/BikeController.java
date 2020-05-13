package BikeRental.BikeRentalREST.bike;

import BikeRental.BikeRentalREST.CustomMessage;
import BikeRental.BikeRentalREST.bike.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class BikeController {

    @Autowired
    BikeService bikeService;

    @GetMapping("/admin/bikes")
    public List<Bike> getBikes(@RequestParam(value = "active") final Optional<Boolean> activeBikes){
        if (activeBikes.isPresent()) {
            return bikeService.getBikes(activeBikes.get());
        } else {
            return bikeService.getBikes();
        }
    }

    @GetMapping("/api/bikes")
    public List<Bike> getActiveBikes(@RequestParam(value = "stationId") final Optional<Long> stationId){
        if (stationId.isPresent()) {
            return bikeService.getBikesByStationId(stationId.get());
        } else {
            return bikeService.getBikes(true);
        }
    }

    @PostMapping("/admin/bikes/deactivate/{id}")
    public CustomMessage deactivateBike(@PathVariable final Long id){
        Optional<Bike> bike = bikeService.deactivateBike(id);
        if (bike.isPresent()){
            return new CustomMessage(1, "Bike deactivated.");
        } else {
            return new CustomMessage(0, "Bike does not exist.");
        }
    }

    @PostMapping("/admin/bikes/add")
    public Bike addBike(@Valid @RequestBody Bike bike){
        return bikeService.addBike(bike);
    }
}
