package BikeRental.BikeRentalREST.station.service;

import BikeRental.BikeRentalREST.station.Station;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StationService {

    List<Station> getAllStations();
    List<Station> getStationsWithAvailableBikes();
    List<Station> getStationsWithAvailableBikesOnAddress(String address);
    boolean addStation(Station station);
    boolean deleteStation(Long stationId);
}
