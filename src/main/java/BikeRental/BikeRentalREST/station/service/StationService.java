package BikeRental.BikeRentalREST.station.service;

import BikeRental.BikeRentalREST.station.Station;

import java.util.List;

public interface StationService {

    List<Station> getAllStations();

    boolean addStation(Station station);

    boolean deleteStation(Long stationId);
}
