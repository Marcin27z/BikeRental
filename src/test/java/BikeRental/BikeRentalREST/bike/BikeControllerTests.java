package BikeRental.BikeRentalREST.bike;

import BikeRental.BikeRentalREST.CustomMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BikeControllerTests extends BikeTestBase {
    private final static String ADMIN_BIKE_ENDPOINT = "/admin/bikes";
    private final static String ADMIN_BIKE_DEACTIVATE_ENDPOINT = "/admin/bikes/deactivate/";
    private final static String ADMIN_BIKE_ADD_ENDPOINT = "/admin/bikes/add";
    private final static String USER_BIKE_ENDPOINT = "/api/bikes";

    private final static boolean ACTIVE = true;
    private final static boolean NONACTIVE = false;

    private final static int ONE_ELEMENT = 1;
    private final static int TWO_ELEMENTS = 2;

    private final static int SUCCESS = 1;
    private final static int FAILURE = 0;

    private final static String RANDOM_ID = "99999";

    private final static String BIKE_NAME = "Name";

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
        logout();
    }

    List<Long> getBikesIds(List<Bike> bikes){
        return bikes.stream()
                .map(Bike::getBikeId)
                .collect(Collectors.toList());
    }

    @Test
    void whenAskForAllBikesAsAdminThenGotAllBikes(){
        loginAsAdmin();
        Bike[] bikes = givenLogged()
                .get( ADMIN_BIKE_ENDPOINT)
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(Bike[].class);

        List<Bike> bikeList = Arrays.asList(bikes);
        List<Long> ids = getBikesIds(bikeList);

        assertEquals(TWO_ELEMENTS, bikeList.size());
        assertTrue(ids.contains(activeTestBike.getBikeId()));
        assertTrue(ids.contains(nonActiveTestBike.getBikeId()));
    }

    @Test
    void whenAskForAllActiveBikesAsAdminThenGotAllActiveBikes(){
        loginAsAdmin();
        Bike[] bikes = givenLogged()
                .param("active", true)
                .get(ADMIN_BIKE_ENDPOINT)
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(Bike[].class);

        List<Bike> bikeList = Arrays.asList(bikes);
        List<Long> ids = getBikesIds(bikeList);

        assertEquals(ONE_ELEMENT, bikeList.size());
        assertTrue(ids.contains(activeTestBike.getBikeId()));
    }

    @Test
    void whenAskForAllNonActiveBikesAsAdminThenGotAllNonActiveBikes(){
        loginAsAdmin();
        Bike[] bikes = givenLogged()
                .param("active", false)
                .get(ADMIN_BIKE_ENDPOINT)
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(Bike[].class);

        List<Bike> bikeList = Arrays.asList(bikes);
        List<Long> ids = getBikesIds(bikeList);

        assertEquals(ONE_ELEMENT, bikeList.size());
        assertTrue(ids.contains(nonActiveTestBike.getBikeId()));
    }

    @Test
    void whenAskForAllBikesAsUnprivilegedUserThenGotActiveBikes(){
        loginAsUnprivilegedUser();

        Bike[] bikes = givenLogged()
                .get(USER_BIKE_ENDPOINT)
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(Bike[].class);

        List<Bike> bikeList = Arrays.asList(bikes);
        List<Long> ids = getBikesIds(bikeList);

        assertEquals(ONE_ELEMENT, bikeList.size());
        assertTrue(ids.contains(activeTestBike.getBikeId()));
    }

    @Test
    void whenAskForBikesAtStationAsUnprivilegedUserThenGotCorrectBikes(){
        loginAsUnprivilegedUser();
        Bike[] bikes = givenLogged()
                .param("stationId", activeTestBike.getStation().getStationId())
                .get(USER_BIKE_ENDPOINT)
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(Bike[].class);

        List<Bike> bikeList = Arrays.asList(bikes);
        List<Long> ids = getBikesIds(bikeList);

        assertEquals(ONE_ELEMENT, bikeList.size());
        assertTrue(ids.contains(activeTestBike.getBikeId()));
    }

    @Test
    void whenDeactivateExistingBikeThenGotItDeactivated(){
        loginAsAdmin();
        CustomMessage message = givenLogged()
                .post(ADMIN_BIKE_DEACTIVATE_ENDPOINT + activeTestBike.getBikeId().toString())
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(CustomMessage.class);

        assertEquals(SUCCESS, message.getCode());
        assertEquals("Bike deactivated.", message.getText());
    }

    @Test
    void whenDeactivateNonExistingBikeThenGotError(){
        loginAsAdmin();
        CustomMessage message = givenLogged()
                .post(ADMIN_BIKE_DEACTIVATE_ENDPOINT + RANDOM_ID)
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(CustomMessage.class);

        assertEquals(FAILURE, message.getCode());
        assertEquals("Bike does not exist.", message.getText());
    }

    @Test
    void whenAddBikeThenGotItAdded(){
        loginAsAdmin();

        Map<String, String> request = new HashMap<>();
        request.put("name", BIKE_NAME);

        Bike newBike = givenLogged()
                .body(request)
                .accept("application/json")
                .contentType("application/json")
                .post(ADMIN_BIKE_ADD_ENDPOINT)
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(Bike.class);

        assertNotNull(newBike.getBikeId());
        assertEquals(BIKE_NAME, newBike.getName());
    }
}
