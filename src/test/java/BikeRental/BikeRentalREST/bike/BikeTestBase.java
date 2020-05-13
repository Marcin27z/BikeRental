package BikeRental.BikeRentalREST.bike;

import BikeRental.BikeRentalREST.station.Station;
import BikeRental.BikeRentalREST.station.StationRepository;
import BikeRental.BikeRentalREST.user.LoggedUserTestBase;
import org.springframework.beans.factory.annotation.Autowired;

public class BikeTestBase extends LoggedUserTestBase {
    protected final static String BIKE_TEST_NAME = "test";
    protected final static String STATION_TEST_ADDRESS = "Test 1A/23";

    @Autowired
    protected BikeRepository bikeRepository;

    @Autowired
    protected StationRepository stationRepository;

    protected Bike createTestBike(String name, Station station, Status status){
        Bike bike = new Bike();
        bike.setName(name);
        bike.setStation(station);
        bike.setStatus(status);

        bikeRepository.save(bike);
        return bike;
    }

    protected Station createTestStation(String address){
        Station station = new Station();
        station.setAddress(address);

        stationRepository.save(station);
        return station;
    }

    protected Bike createTestBike(boolean isActive){
        Station station = createTestStation(STATION_TEST_ADDRESS);
        return createTestBike(BIKE_TEST_NAME, station, isActive ? Status.FREE : Status.REMOVED);
    }

    protected void deleteAll(){
        bikeRepository.deleteAll();
        stationRepository.deleteAll();
    }
}
