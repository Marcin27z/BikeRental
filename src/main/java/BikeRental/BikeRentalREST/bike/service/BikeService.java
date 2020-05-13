package BikeRental.BikeRentalREST.bike.service;

import BikeRental.BikeRentalREST.bike.Bike;
import BikeRental.BikeRentalREST.station.Station;

import java.util.List;
import java.util.Optional;

public interface BikeService {
    List<Bike> getBikes();
    List<Bike> getBikes(boolean activeBikes);
    List<Bike> getBikesByStationId(Long stationId);
    Optional<Bike> getBikeById(Long id);
    Optional<Bike> activateBike(Long id);
    Optional<Bike> deactivateBike(Long id);
    Optional<Bike> relocateBike(Long bikeId, Station station);
    Bike addBike(Bike bike);
}
