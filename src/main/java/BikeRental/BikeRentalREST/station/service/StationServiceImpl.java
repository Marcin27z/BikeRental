package BikeRental.BikeRentalREST.station.service;

import BikeRental.BikeRentalREST.station.Station;
import BikeRental.BikeRentalREST.station.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public boolean addStation(Station station) {
        if (station == null) {
            return false;
        }
        if (!stationRepository.findByAddress(station.getAddress()).isPresent()) {
            stationRepository.save(station);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteStation(Long stationId) {
        if (stationId == null) {
            return false;
        }
        return stationRepository.setStationDeleted(stationId) == 1;
    }
}
