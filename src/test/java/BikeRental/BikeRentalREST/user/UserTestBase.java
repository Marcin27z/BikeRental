package BikeRental.BikeRentalREST.user;

import BikeRental.BikeRentalREST.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTestBase {
    private final static String UNIQUE_TEST_PREFIX = "ut";
    private final static String EMAIL_POSTFIX = "@mail.com";

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserRepository userRepository;

    protected User createUser(boolean isAdmin, boolean isActive) {
        String email = UNIQUE_TEST_PREFIX + UUID.randomUUID().toString() + EMAIL_POSTFIX;
        String username = UNIQUE_TEST_PREFIX + UUID.randomUUID().toString();
        String password = UNIQUE_TEST_PREFIX + UUID.randomUUID().toString();
        String phoneNumber = generateRandomPhoneNumber();
        String token = UNIQUE_TEST_PREFIX + UUID.randomUUID().toString();
        return createUser(email, username, password, phoneNumber, isAdmin, isActive, token);
    }

    private String generateRandomPhoneNumber(){
        Random generator = new Random();

        int num1 = 0;
        int num2 = 0;
        int num3 = 0;

        num1 = generator.nextInt(600) + 100;
        num2 = generator.nextInt(641) + 100;
        num3 = generator.nextInt(641) + 100;

        return Integer.toString(num1) + Integer.toString(num2) + Integer.toString(num3);
    }

    protected User createUser(String email, String username, String password, String phoneNumber,
                    boolean isAdmin, boolean isActive, String token) {
        User user = new User(email, username, password, phoneNumber);
        user.setAdmin(isAdmin);
        user.setActive(isActive);
        user.setToken(token);
        userRepository.save(user);

        return user;
    }

    protected User createUser() {
        return createUser(true, true);
    }

    protected void deleteAllUsers() {
        userRepository.deleteAll();
    }

}
