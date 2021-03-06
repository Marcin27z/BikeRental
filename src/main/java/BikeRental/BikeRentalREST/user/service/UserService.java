package BikeRental.BikeRentalREST.user.service;

import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.login.LoginInfo;
import BikeRental.BikeRentalREST.user.security.MyUserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    LoginInfo login(String username, String password);
    Optional<User> findUserByToken(String token);
    Optional<MyUserDetails> findByToken(String token);
    Optional<User> findById(Long id);
    List<User> getUsers();
    List<User> getUsers(boolean activeUsers);
    Optional<User> deactivateUser(Long id);
    Optional<User> changePermissions(Long id, boolean isAdmin);
}
