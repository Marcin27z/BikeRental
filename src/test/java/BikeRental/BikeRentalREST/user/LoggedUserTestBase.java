package BikeRental.BikeRentalREST.user;

import BikeRental.BikeRentalREST.user.service.UserService;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.jayway.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoggedUserTestBase {
    private final static String USERNAME = "user321";
    private final static String PASSWORD = "password123";
    private final static String EMAIL = "user@mail.com";
    private final static String PHONE_NUMBER = "12121212";

    @LocalServerPort
    int port;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private User loggedUser;
    private String token;

    void loginAsAdmin() {
        login(true);
    }

    void loginAsUnprivilegedUser() {
        login(false);
    }

    void login(boolean isAdmin){
        loggedUser = createUser(isAdmin, true);
        token = userService.login(USERNAME, PASSWORD);
    }

    User createUser(boolean isAdmin, boolean isActive) {
        User user = new User(EMAIL, USERNAME, PASSWORD, PHONE_NUMBER);
        user.setAdmin(isAdmin);
        user.setActive(isActive);
        userRepository.save(user);

        return user;
    }

    void logout() {
        userRepository.delete(loggedUser);
    }

    RequestSpecification givenLogged() {
        return given().port(port)
                .authentication().oauth2(getToken());
    }

    String getToken() {
        return token;
    }
}
