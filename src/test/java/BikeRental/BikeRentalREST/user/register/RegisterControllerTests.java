package BikeRental.BikeRentalREST.user.register;

import BikeRental.BikeRentalREST.CustomMessage;
import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.UserTestBase;
import com.jayway.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

class RegisterControllerTests extends UserTestBase {
    private final static String REGISTER_ENDPOINT = "/register";

    private final static String USERNAME = "real2";
    private final static String PASSWORD = "password2";
    private final static String EMAIL = "mail2@mail.com";
    private final static String PHONE_NUMBER = "55555551";

    @LocalServerPort
    int port;

    @AfterEach
    void tearDown() {
        deleteAllUsers();
    }

    @Test
    void whenRegisterThenGotRegistered() {
        Map<String, String> request = new HashMap<>();
        request.put("email", EMAIL);
        request.put("login", USERNAME);
        request.put("password", PASSWORD);
        request.put("phoneNumber", PHONE_NUMBER);

        CustomMessage message = given().port(port)
                .body(request)
                .accept("application/json")
                .contentType("application/json")
                .post(REGISTER_ENDPOINT)
                .then()
                .extract()
                .body().as(CustomMessage.class);

        assertEquals(1, message.getCode());
        assertEquals("User created!", message.getText());
    }

    @Test
    void whenRegisterWithDoubledEmailThenAccountIsNotRegistered() {
        User user = createUser();

        Map<String, String> request = new HashMap<>();
        request.put("email", user.getEmail());
        request.put("login", USERNAME);
        request.put("password", PASSWORD);
        request.put("phoneNumber", PHONE_NUMBER);

        CustomMessage message = given().port(port)
                .body(request)
                .accept("application/json")
                .contentType("application/json")
                .post(REGISTER_ENDPOINT)
                .then()
                .extract()
                .body().as(CustomMessage.class);

        assertEquals(0, message.getCode());
        assertEquals("This mail is taken", message.getText());
    }

    @Test
    void whenRegisterWithDoubledLoginThenAccountIsNotRegistered() {
        User user = createUser();

        Map<String, String> request = new HashMap<>();
        request.put("email", EMAIL);
        request.put("login", user.getLogin());
        request.put("password", PASSWORD);
        request.put("phoneNumber", PHONE_NUMBER);

        CustomMessage message = given().port(port)
                .body(request)
                .accept("application/json")
                .contentType("application/json")
                .post(REGISTER_ENDPOINT)
                .then()
                .extract()
                .body().as(CustomMessage.class);

        assertEquals(0, message.getCode());
        assertEquals("This login is taken", message.getText());
    }

    @Test
    void whenRegisterWithDoubledPhoneNumberThenAccountIsNotRegistered() {
        User user = createUser();

        Map<String, String> request = new HashMap<>();
        request.put("email", EMAIL);
        request.put("login", USERNAME);
        request.put("password", PASSWORD);
        request.put("phoneNumber", user.getPhoneNumber());

        CustomMessage message = given().port(port)
                .body(request)
                .accept("application/json")
                .contentType("application/json")
                .post(REGISTER_ENDPOINT)
                .then()
                .extract()
                .body().as(CustomMessage.class);

        assertEquals(0, message.getCode());
        assertEquals("This phone number is taken", message.getText());
    }
}

