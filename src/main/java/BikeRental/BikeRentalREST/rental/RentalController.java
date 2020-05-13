package BikeRental.BikeRentalREST.rental;

import BikeRental.BikeRentalREST.CustomMessage;
import BikeRental.BikeRentalREST.bike.Bike;
import BikeRental.BikeRentalREST.bike.service.BikeService;
import BikeRental.BikeRentalREST.rental.service.RentalService;
import BikeRental.BikeRentalREST.station.Station;
import BikeRental.BikeRentalREST.station.service.StationService;
import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class RentalController {
    @Autowired
    RentalService rentalService;
    @Autowired
    UserService userService;
    @Autowired
    StationService stationService;
    @Autowired
    BikeService bikeService;

    @GetMapping("/api/rentals")
    List<Rental> getRentals(@RequestParam final Optional<Long> UserId){
        if(UserId.isPresent()){
            return this.rentalService.getRentalsByUserId(UserId.get());
        }else {
            return this.rentalService.getRentals();
        }
    }

    @PostMapping("/api/rentals")
    CustomMessage makeRental(@RequestParam final Long userId, @RequestParam final Long stationId){
        Rental rental = new Rental();
        Optional<User> user = userService.findById(userId);
        if(!user.isPresent()){
            return new CustomMessage(0, "User does not exist!");
        }
        Optional<Station> station = stationService.findById(stationId);
        if(!station.isPresent()){
            return new CustomMessage(0, "Station does not exist!");
        }
        List<Bike> bikesList = bikeService.getBikesByStationId(stationId);
        if(bikesList.size() == 0){
            return new CustomMessage(0, "No bikes are available!");
        }
        rental.setUser(user.get());
        rental.setBike(bikesList.get(0));
        bikeService.deactivateBike(bikesList.get(0).getBikeId());
        rental.setRentalDate(LocalDateTime.now());
        rentalService.addNewRental(rental);
        return new CustomMessage(1, "Rental was made! Bike id = " + rental.getBike().getBikeId().toString());
    }

    @PutMapping("/api/rentals")
    CustomMessage endRental(@RequestParam final Long userId, @RequestParam final Long stationId){
        Optional<Rental> rental = this.rentalService.getOpenRentalByUserId(userId);
        if(!rental.isPresent()){
            return new CustomMessage(0, "no unfinished rentals!");
        }
        Optional<Station> station = this.stationService.findById(stationId);
        if(!station.isPresent()){
            return new CustomMessage(0, "station does not exist!");
        }
        if(this.bikeService.activateBike(rental.get().getBike().getBikeId()).isPresent()){
            return new CustomMessage(0, "bike not found!");
        }
        this.bikeService.relocateBike(rental.get().getBike().getBikeId(), station.get());

        if(rentalService.endRental(userId)){
            return new CustomMessage(1, "Return successful!");
        }else{
            return new CustomMessage(0, "Return failed!");
        }
    }
}
