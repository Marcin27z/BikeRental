package BikeRental.BikeRentalREST.bike.service;

import BikeRental.BikeRentalREST.bike.Bike;

import java.util.List;
import java.util.Optional;

public interface BikeService {
    List<Bike> getBikes();
    List<Bike> getBikes(boolean activeBikes);
    List<Bike> getBikesByStationId(Long stationId);
    Optional<Bike> findById(Long id);
    Optional<Bike> deactivateBike(Long id);
    Bike addBike(Bike bike);
}
