package BikeRental.BikeRentalREST.station.service;

import BikeRental.BikeRentalREST.station.Station;

import java.util.List;
import java.util.Optional;

public interface StationService {

    List<Station> getAllStations();

    boolean addStation(Station station);

    boolean deleteStation(Long stationId);

    Optional<Station> findById(Long stationId);
}
