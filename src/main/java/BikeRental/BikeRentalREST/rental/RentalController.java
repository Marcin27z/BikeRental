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
import java.util.ArrayList;
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

    @CrossOrigin()
    @GetMapping("/admin/rentals")
    List<Rental> getRentalsAsAdmin(@RequestParam final Optional<Long> UserId){
        if(UserId.isPresent()){
            return this.rentalService.getRentalsByUserId(UserId.get());
        }else {
            return this.rentalService.getRentals();
        }
    }

    @CrossOrigin()
    @GetMapping("/api/rentals")
    List<Rental> getRentalsAsUser(@RequestHeader(name="Authorization") String token){
        Optional<User> user = userService.findUserByToken(token);
        if(!user.isPresent()){
            return new ArrayList<>();
        }
        return this.rentalService.getRentalsByUserId(user.get().getUserId());
    }

    @CrossOrigin()
    @PostMapping("/api/rentals")
    CustomMessage makeRental(@RequestParam final Long stationId, @RequestHeader(name="Authorization") String token){
        Optional<User> user = userService.findUserByToken(token);
        if(!user.isPresent()){
            return new CustomMessage(0, "User not found!");
        }
        if(this.rentalService.getOpenRentalByUserId(user.get().getUserId()).isPresent()){
            return new CustomMessage(0, "There is existing rental for this user!");
        }
        Optional<Station> station = stationService.findById(stationId);
        if(!station.isPresent()){
            return new CustomMessage(0, "Station does not exist!");
        }
        List<Bike> bikesList = bikeService.getBikesByStationId(stationId);
        if(bikesList.size() == 0){
            return new CustomMessage(0, "No bikes are available!");
        }
        Rental rental = new Rental();
        rental.setUser(user.get());
        rental.setBike(bikesList.get(0));
        bikeService.rentBike(bikesList.get(0).getBikeId());
        rental.setRentalDate(LocalDateTime.now());
        rentalService.addNewRental(rental);
        return new CustomMessage(1, "Rental was made! Bike id = " + rental.getBike().getBikeId().toString());
    }

    @CrossOrigin()
    @PutMapping("/api/rentals")
    CustomMessage endRental(@RequestParam final Long stationId, @RequestHeader(name="Authorization") String token){
        Optional<User> user = userService.findUserByToken(token);
        if(!user.isPresent()){
            return new CustomMessage(0, "User not found!");
        }
        Optional<Rental> rental = this.rentalService.getOpenRentalByUserId(user.get().getUserId());
        if(!rental.isPresent()){
            return new CustomMessage(0, "No unfinished rentals!");
        }
        Optional<Station> station = this.stationService.findById(stationId);
        if(!station.isPresent()){
            return new CustomMessage(0, "Station does not exist!");
        }
        if(this.bikeService.freeBike(rental.get().getBike().getBikeId()).isPresent()){
            return new CustomMessage(0, "Bike not found!");
        }
        this.bikeService.relocateBike(rental.get().getBike().getBikeId(), station.get());

        if(rentalService.endRental(user.get().getUserId())){
            return new CustomMessage(1, "Return successful!");
        }else{
            return new CustomMessage(0, "Return failed!");
        }
    }
}
