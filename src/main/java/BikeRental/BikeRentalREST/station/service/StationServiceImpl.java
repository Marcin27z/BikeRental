package BikeRental.BikeRentalREST.station.service;

import BikeRental.BikeRentalREST.station.Station;
import BikeRental.BikeRentalREST.station.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StationServiceImpl implements StationService{

    private StationRepository stationRepository;

    @Autowired
    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public List<Station> getAllStations() {
        return stationRepository.findAllByDeletedIsFalse();
    }

    @Override
    public List<Station> getStationsWithAvailableBikes() {
        return stationRepository.findAllWithAvailableBikes();
    }

    @Override
    public List<Station> getStationsWithAvailableBikesOnAddress(String address) {
        return stationRepository.findAllWithAvailableBikesOnAddress(address);
    }


    @Override
    public boolean addStation(Station station) {
        if (!stationRepository.findByAddress(station.getAddress()).isPresent()) {
            station.setDeleted(false);
            stationRepository.save(station);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteStation(Long stationId) {
        return stationRepository.setStationDeleted(stationId) == 1;
    }

    @Override
    public Optional<Station> findById(Long stationId) {
        return this.stationRepository.findById(stationId);
    }
}
