package BikeRental.BikeRentalREST.user.service;

import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.UserRepository;
import BikeRental.BikeRentalREST.user.security.MyUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class UserServiceImplTests {
    private final static String USERNAME = "user123";
    private final static String PASSWORD = "password123";
    private final static String EMAIL = "mail123@mail.com";
    private final static String PHONE_NUMBER = "55557777";
    private final static String TOKEN = "123456789";

    private final static String FAKE_USERNAME = "fake";
    private final static String FAKE_PASSWORD = "fake";

    private final static  String EMPTY_TOKEN = "";

    private final static int ONE_ELEMENT = 1;
    private final static int TWO_ELEMENTS = 2;
    private final static int FIRST_LIST_ELEMENT = 0;

    private static User testUser;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() {
        deleteUsers();
        testUser = createTestUser(true, true);
    }

    User createTestUser(boolean isAdmin, boolean isActive){
        User testUser = new User(EMAIL, USERNAME, PASSWORD, PHONE_NUMBER);
        testUser.setToken(TOKEN);
        testUser.setAdmin(isAdmin);
        testUser.setActive(isActive);
        userRepository.save(testUser);

        return testUser;
    }

    void deleteUsers(){
        userRepository.deleteAll();
    }

    @Test
    void whenLoginToExistingUserThenGotToken(){
        String token = userService.login(USERNAME, PASSWORD);
        assertNotEquals(EMPTY_TOKEN, token);
        assertTrue(token.length() > 0);
    }

    @Test
    void whenLoginToNonExistingUserThenGotNoToken(){
        String token = userService.login(FAKE_USERNAME, FAKE_PASSWORD);
        assertEquals(EMPTY_TOKEN, token);
    }

    @Test
    void whenFindByTokenThenGotCorrectUserDetails(){
        Optional<MyUserDetails> user = userService.findByToken(TOKEN);
        assertTrue(user.isPresent());
        assertEquals(testUser.getLogin(),  user.get().getUsername());
    }

    @Test
    void whenFindByIdThenGotCorrectUser(){
        Optional<User> user = userService.findById(testUser.getUserId());
        assertTrue(user.isPresent());
        assertEquals(testUser.getUserId(),  user.get().getUserId());
    }

    @Test
    void whenGetUsersThenGotAllUsers(){
        createTestUser(false, false);
        List<User> users = userService.getUsers();

        assertEquals(TWO_ELEMENTS,  users.size());
    }

    @Test
    void whenGetActiveUsersThenGotOnlyActiveUsers(){
        createTestUser(false, false);
        List<User> users = userService.getUsers(true);

        assertEquals(ONE_ELEMENT,  users.size());
        assertEquals(testUser.getUserId(), users.get(FIRST_LIST_ELEMENT).getUserId());
    }

    @Test
    void whenGetNoActiveUsersThenGotOnlyNonActiveUsers(){
        User user = createTestUser(false, false);
        List<User> users = userService.getUsers(false);

        assertEquals(ONE_ELEMENT,  users.size());
        assertEquals(user.getUserId(), users.get(FIRST_LIST_ELEMENT).getUserId());
    }

    @Test
    void whenDeactivateUserThenGotItDeactivated(){
        Optional<User> user = userService.deactivateUser(testUser.getUserId());

        assertTrue(user.isPresent());
        assertEquals(testUser.getUserId(), user.get().getUserId());
        assertFalse(user.get().isActive());
    }

    @Test
    void whenChangePermissionsThenGotChanged(){
        Optional<User> user = userService.changePermissions(testUser.getUserId(), false);

        assertTrue(user.isPresent());
        assertEquals(testUser.getUserId(), user.get().getUserId());
        assertFalse(user.get().isAdmin());
    }
}
