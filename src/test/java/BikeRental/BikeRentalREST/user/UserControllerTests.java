package BikeRental.BikeRentalREST.user;

import BikeRental.BikeRentalREST.CustomMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserControllerTests extends LoggedUserTestBase {
    private final static String RANDOM = "123456789";

    private final static String USERS_ENDPOINT = "/admin/users";
    private final static String USERS_DEACTIVATE_ENDPOINT = USERS_ENDPOINT + "/deactivate/";
    private final static String USERS_PERMISSIONS_ENDPOINT = USERS_ENDPOINT + "/permissions/";

    private final static int ONE_ELEMENT = 1;
    private final static int TWO_ELEMENTS = 2;
    private final static int THREE_ELEMENTS = 3;

    private User testActiveUser;
    private User testNonActiveUser;

    @BeforeEach
    void setUp() {
        deleteAllUsers();
        loginAsAdmin();
        createTestUsers();
    }

    void createTestUsers() {
        testActiveUser = createUser(true, true);
        testNonActiveUser = createUser(false, false);
    }

    @AfterEach
    void tearDown() {
        logout();
    }

    @Test
    void whenGetAllUsersListThenGotAllUsers() {
        User[] users = givenLogged()
                .get(USERS_ENDPOINT)
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(User[].class);

        List<User> userList = Arrays.asList(users);
        List<Long> userIdList = userList.stream().map(User::getUserId).collect(Collectors.toList());

        assertEquals(THREE_ELEMENTS,  userList.size());
        assertTrue(userIdList.contains(testActiveUser.getUserId()));
        assertTrue(userIdList.contains(testNonActiveUser.getUserId()));
    }

    @Test
    void whenGetAllActiveUsersListThenGotAllActiveUsers() {
        User[] users = givenLogged()
                .param("active", true)
                .get(USERS_ENDPOINT)
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(User[].class);

        List<User> userList = Arrays.asList(users);
        List<Long> userIdList = userList.stream().map(User::getUserId).collect(Collectors.toList());

        assertEquals(TWO_ELEMENTS,  userList.size());
        assertTrue(userIdList.contains(testActiveUser.getUserId()));
    }

    @Test
    void whenGetAllNonActiveUsersListThenGotAllNonActiveUsers() {
        User[] users = givenLogged()
                .param("active", false)
                .get(USERS_ENDPOINT)
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(User[].class);

        List<User> userList = Arrays.asList(users);
        List<Long> userIdList = userList.stream().map(User::getUserId).collect(Collectors.toList());

        assertEquals(ONE_ELEMENT,  userList.size());
        assertTrue(userIdList.contains(testNonActiveUser.getUserId()));
    }

    @Test
    void whenDeactivateUserThenGotItDeactivated() {
        Long id = testActiveUser.getUserId();

        CustomMessage message = givenLogged()
                .post(USERS_DEACTIVATE_ENDPOINT + id.toString())
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(CustomMessage.class);

        Optional<User> user = userRepository.findById(id);
        assert(user.isPresent());
        assertEquals(1, message.getCode());
        assertEquals("User deactivated.", message.getText());
        assertFalse(user.get().isActive());
    }

    @Test
    void whenDeactivateUserThatDoesNotExistThenGotUserNotFoundMessage() {
        CustomMessage message = givenLogged()
                .post(USERS_DEACTIVATE_ENDPOINT + RANDOM)
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(CustomMessage.class);

        assertEquals(0, message.getCode());
        assertEquals("User does not exist.", message.getText());
    }

    @Test
    void whenChangePermissionThenGotItChanged() {
        Long id = testActiveUser.getUserId();

        CustomMessage message = givenLogged()
                .param("isAdmin", false)
                .post(USERS_PERMISSIONS_ENDPOINT + id.toString())
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(CustomMessage.class);

        Optional<User> user = userRepository.findById(id);
        assert(user.isPresent());
        assertEquals(1, message.getCode());
        assertEquals("User permissions changed.", message.getText());
        assertFalse(user.get().isAdmin());
    }

    @Test
    void whenChangePermissionOfUserThatDoesNotExistThenGotUserNotFoundMessage() {
        CustomMessage message = givenLogged()
                .param("isAdmin", false)
                .post(USERS_PERMISSIONS_ENDPOINT + RANDOM)
                .then().assertThat().statusCode(200)
                .extract()
                .body().as(CustomMessage.class);

        assertEquals(0, message.getCode());
        assertEquals("User does not exist.", message.getText());
    }
}
