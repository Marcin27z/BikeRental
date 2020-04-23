package BikeRental.BikeRentalREST.user.service;

import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.UserTestBase;
import BikeRental.BikeRentalREST.user.login.LoginInfo;
import BikeRental.BikeRentalREST.user.security.MyUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class UserServiceImplTests extends UserTestBase {
    private final static String FAKE_USERNAME = "fake";
    private final static String FAKE_PASSWORD = "fake";

    private final static int ONE_ELEMENT = 1;
    private final static int TWO_ELEMENTS = 2;
    private final static int FIRST_LIST_ELEMENT = 0;

    private static User testUser;

    @BeforeEach
    void setUp() {
        deleteAllUsers();
        testUser = createUser(true, true);
    }

    @Test
    void whenLoginToExistingUserThenGotToken(){
        LoginInfo loginInfo = userService.login(testUser.getLogin(), testUser.getPassword());
        String token = loginInfo.getToken();

        assertNotEquals(StringUtils.EMPTY, token);
        assertTrue(token.length() > 0);
    }

    @Test
    void whenLoginToNonExistingUserThenGotNoToken(){
        LoginInfo loginInfo = userService.login(FAKE_USERNAME, FAKE_PASSWORD);
        String token = loginInfo.getToken();

        assertEquals(StringUtils.EMPTY, token);
    }

    @Test
    void whenFindByTokenThenGotCorrectUserDetails(){
        Optional<MyUserDetails> user = userService.findByToken(testUser.getToken());
        assertTrue(user.isPresent());
        assertEquals(testUser.getLogin(), user.get().getUsername());
    }

    @Test
    void whenFindByIdThenGotCorrectUser(){
        Optional<User> user = userService.findById(testUser.getUserId());
        assertTrue(user.isPresent());
        assertEquals(testUser.getUserId(), user.get().getUserId());
    }

    @Test
    void whenGetUsersThenGotAllUsers(){
        createUser(false, false);
        List<User> users = userService.getUsers();

        assertEquals(TWO_ELEMENTS, users.size());
    }

    @Test
    void whenGetActiveUsersThenGotOnlyActiveUsers(){
        createUser(false, false);
        List<User> users = userService.getUsers(true);

        assertEquals(ONE_ELEMENT, users.size());
        assertEquals(testUser.getUserId(), users.get(FIRST_LIST_ELEMENT).getUserId());
    }

    @Test
    void whenGetNoActiveUsersThenGotOnlyNonActiveUsers(){
        User user = createUser(false, false);
        List<User> users = userService.getUsers(false);

        assertEquals(ONE_ELEMENT, users.size());
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
