package BikeRental.BikeRentalREST.user.register;

import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.UserRepository;
import com.jayway.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegisterControllerTests {
    private final static String REGISTER_ENDPOINT = "/register";

    private final static String USERNAME = "real2";
    private final static String PASSWORD = "password2";
    private final static String EMAIL = "mail2@mail.com";
    private final static String PHONE_NUMBER = "55555551";

    private final static String DOUBLED_EMAIL = "doubled@doubled.com";
    private final static String DOUBLED_USERNAME= "doubled";
    private final static String DOUBLED_PHONE_NUMBER = "55555554";

    private final static String UNIQUE_USERNAME = DOUBLED_USERNAME;
    private final static String UNIQUE_PASSWORD = PASSWORD;
    private final static String UNIQUE_EMAIL = DOUBLED_EMAIL;
    private final static String UNIQUE_PHONE_NUMBER = DOUBLED_PHONE_NUMBER;

    @LocalServerPort
    int port;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void removeUsers() {
        userRepository.deleteAll();
    }

    @Test
    void whenRegisterThenGotRegistered() {
        List<User> users = userRepository.findAll();
        System.out.println(users);

        Map<String, String> request = new HashMap<>();
        request.put("email", EMAIL);
        request.put("login", USERNAME);
        request.put("password", PASSWORD);
        request.put("phoneNumber", PHONE_NUMBER);

        Response response = given().port(port)
                .body(request)
                .accept("application/json")
                .contentType("application/json")
                .post(REGISTER_ENDPOINT)
                .then()
                .extract().response();

        assertEquals(1, response.jsonPath().getInt("code"));
        assertEquals("User created!", response.jsonPath().getString("text"));
    }

    @Test
    void whenRegisterWithDoubledEmailThenAccountIsNotRegistered() {
        User user = new User(DOUBLED_EMAIL, USERNAME, PASSWORD, PHONE_NUMBER);
        userRepository.save(user);

        Map<String, String> request = new HashMap<>();
        request.put("email", DOUBLED_EMAIL);
        request.put("login", UNIQUE_USERNAME);
        request.put("password", UNIQUE_PASSWORD);
        request.put("phoneNumber", UNIQUE_PHONE_NUMBER);

        Response response = given().port(port)
                .body(request)
                .accept("application/json")
                .contentType("application/json")
                .post(REGISTER_ENDPOINT)
                .then()
                .extract().response();

        assertEquals(0, response.jsonPath().getInt("code"));
        assertEquals("This mail is taken", response.jsonPath().getString("text"));
    }

    @Test
    void whenRegisterWithDoubledLoginThenAccountIsNotRegistered() {
        User user = new User(EMAIL, DOUBLED_USERNAME, PASSWORD, PHONE_NUMBER);
        userRepository.save(user);

        Map<String, String> request = new HashMap<>();
        request.put("email", UNIQUE_EMAIL);
        request.put("login", DOUBLED_USERNAME);
        request.put("password", UNIQUE_PASSWORD);
        request.put("phoneNumber", UNIQUE_PHONE_NUMBER);

        Response response = given().port(port)
                .body(request)
                .accept("application/json")
                .contentType("application/json")
                .post(REGISTER_ENDPOINT)
                .then()
                .extract().response();

        assertEquals(0, response.jsonPath().getInt("code"));
        assertEquals("This login is taken", response.jsonPath().getString("text"));
    }

    @Test
    void whenRegisterWithDoubledPhoneNumberThenAccountIsNotRegistered() {
        User user = new User(EMAIL, USERNAME, PASSWORD, DOUBLED_PHONE_NUMBER);
        userRepository.save(user);

        Map<String, String> request = new HashMap<>();
        request.put("email", UNIQUE_EMAIL);
        request.put("login", UNIQUE_USERNAME);
        request.put("password", UNIQUE_PASSWORD);
        request.put("phoneNumber", DOUBLED_PHONE_NUMBER);

        Response response = given().port(port)
                .body(request)
                .accept("application/json")
                .contentType("application/json")
                .post(REGISTER_ENDPOINT)
                .then()
                .extract().response();

        assertEquals(0, response.jsonPath().getInt("code"));
        assertEquals("This phone number is taken", response.jsonPath().getString("text"));
    }
}

