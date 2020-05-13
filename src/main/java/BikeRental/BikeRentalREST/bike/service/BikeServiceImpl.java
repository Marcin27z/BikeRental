package BikeRental.BikeRentalREST.bike.service;

import BikeRental.BikeRentalREST.bike.Bike;
import BikeRental.BikeRentalREST.bike.BikeRepository;
import BikeRental.BikeRentalREST.bike.Status;
import BikeRental.BikeRentalREST.station.Station;
import BikeRental.BikeRentalREST.station.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("bikeService")
public class BikeServiceImpl implements BikeService {

    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    StationRepository stationRepository;

    @Override
    public List<Bike> getBikes() {
        return bikeRepository.findAll();
    }

    @Override
    public List<Bike> getBikes(boolean activeBikes) {
        List<Bike> bikesList = bikeRepository.findAll();

        return bikesList.stream()
                .filter(b -> b.isActive() == activeBikes)
                .collect(Collectors.toList());
    }

    @Override
    public List<Bike> getBikesByStationId(Long stationId) {
        Optional<Station> station = stationRepository.findById(stationId);
        if (station.isPresent()){
            List<Bike> bikes = bikeRepository.getBikesByStation(station.get());

            return bikes.stream()
                    .filter(Bike::isActive)
                    .collect(Collectors.toList());
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public Optional<Bike> findById(Long id) {
        return bikeRepository.findById(id);
    }

    @Override
    public Optional<Bike> deactivateBike(Long id) {
        Optional<Bike> bike = findById(id);
        bike.ifPresent(b -> {
            b.setStatus(Status.REMOVED);
            bikeRepository.save(b);
        });

        return bike;
    }

    @Override
    public Bike addBike(Bike bike) {
        bike.setStatus(Status.FREE);
        return bikeRepository.save(bike);
    }
}
