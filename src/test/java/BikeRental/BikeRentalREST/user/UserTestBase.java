package BikeRental.BikeRentalREST.user;

import BikeRental.BikeRentalREST.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTestBase {
    private final static String UNIQUE_TEST_PREFIX = "ut";
    private final static String EMAIL_POSTFIX = "@mail.com";

    private final static int START_PHONE_NUMBER = 100000000;
    private final static int END_PHONE_NUMBER = 999999999;

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    protected User createUser(boolean isAdmin, boolean isActive) {
        String email = UNIQUE_TEST_PREFIX + UUID.randomUUID().toString() + EMAIL_POSTFIX;
        String username = UNIQUE_TEST_PREFIX + UUID.randomUUID().toString();
        String password = UNIQUE_TEST_PREFIX + UUID.randomUUID().toString();
        String phoneNumber = generateRandomPhoneNumber();
        String token = UNIQUE_TEST_PREFIX + UUID.randomUUID().toString();
        return createUser(email, username, password, phoneNumber, isAdmin, isActive, token);
    }

    protected User createUser(String email, String username, String password, String phoneNumber,
                              boolean isAdmin, boolean isActive, String token) {
        User user = new User(email, username, passwordEncoder.encode(password), phoneNumber);
        user.setAdmin(isAdmin);
        user.setActive(isActive);
        user.setToken(token);
        userRepository.save(user);

        user.setPassword(password);
        return user;
    }

    protected User createUser() {
        return createUser(true, true);
    }

    protected void deleteAllUsers() {
        userRepository.deleteAll();
    }

    private String generateRandomPhoneNumber(){
        Random generator = new Random();

        return Integer.toString(START_PHONE_NUMBER + generator.nextInt(END_PHONE_NUMBER - START_PHONE_NUMBER));
    }

}
