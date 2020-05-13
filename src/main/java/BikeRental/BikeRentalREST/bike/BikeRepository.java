package BikeRental.BikeRentalREST.bike;

import BikeRental.BikeRentalREST.station.Station;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BikeRepository extends CrudRepository<Bike, Long> {
    List<Bike> findAll();
    List<Bike> getBikesByStation(Station station);
}
