package BikeRental.BikeRentalREST.bike.service;

import BikeRental.BikeRentalREST.bike.Bike;
import BikeRental.BikeRentalREST.bike.BikeRepository;
import BikeRental.BikeRentalREST.bike.BikeTestBase;
import BikeRental.BikeRentalREST.bike.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BikeServiceImplTests extends BikeTestBase {
    private final static boolean ACTIVE = true;
    private final static boolean NONACTIVE = false;

    private final static int ONE_ELEMENT = 1;
    private final static int TWO_ELEMENTS = 2;

    @Autowired
    BikeService bikeService;

    @Autowired
    BikeRepository bikeRepository;

    private Bike activeTestBike;
    private Bike nonActiveTestBike;

    @BeforeEach
    void setUp(){
        activeTestBike = createTestBike(ACTIVE);
        nonActiveTestBike = createTestBike(NONACTIVE);
    }

    @AfterEach
    void tearDown(){
        deleteAll();
    }

    List<Long> getBikesIds(List<Bike> bikes){
        return bikes.stream()
                .map(Bike::getBikeId)
                .collect(Collectors.toList());
    }

    @Test
    void whenGetBikesThenGotAllBikes(){
        List<Bike> bikes = bikeRepository.findAll();
        List<Long> ids = getBikesIds(bikes);

        assertEquals(TWO_ELEMENTS, bikes.size());
        assertTrue(ids.contains(activeTestBike.getBikeId()));
        assertTrue(ids.contains(nonActiveTestBike.getBikeId()));
    }

    @Test
    void whenGetActiveBikesThenGotAllActiveBikes(){
        List<Bike> bikes = bikeService.getBikes(ACTIVE);
        List<Long> ids = getBikesIds(bikes);

        assertEquals(ONE_ELEMENT, bikes.size());
        assertTrue(ids.contains(activeTestBike.getBikeId()));
    }

    @Test
    void whenGetNonActiveBikesThenGotAllNonActiveBikes(){
        List<Bike> bikes = bikeService.getBikes(NONACTIVE);
        List<Long> ids = getBikesIds(bikes);

        assertEquals(ONE_ELEMENT, bikes.size());
        assertTrue(ids.contains(nonActiveTestBike.getBikeId()));
    }

    @Test
    void whenGetBikesByStationIdThenGotAllBikesAssociatedWithStation(){
        Long stationId = activeTestBike.getStation().getStationId();

        List<Bike> bikes = bikeService.getBikesByStationId(stationId);
        List<Long> ids = getBikesIds(bikes);

        assertEquals(ONE_ELEMENT, bikes.size());
        assertTrue(ids.contains(activeTestBike.getBikeId()));
    }

    @Test
    void whenDeactivateBikeThenGotItDeactivated(){
        Optional<Bike> bike = bikeService.deactivateBike(activeTestBike.getBikeId());

        assertTrue(bike.isPresent());
        assertFalse(bike.get().isActive());
        assertEquals(activeTestBike.getBikeId(), bike.get().getBikeId());
    }

    @Test
    void whenAddBikeThenNewIdIsGiven(){
        Bike bike = new Bike();

        assertNotNull(bikeService.addBike(bike).getBikeId());
    }

    @Test
    void whenAddBikeThenBikeIsFree(){
        Bike bike = new Bike();

        assertEquals(Status.FREE, bikeService.addBike(bike).getStatus());
    }
}
