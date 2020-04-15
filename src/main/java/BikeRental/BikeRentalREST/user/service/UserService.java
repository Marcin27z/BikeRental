package BikeRental.BikeRentalREST.user.service;

import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.security.MyUserDetails;

import java.util.Optional;

public interface UserService {
    String login(String username, String password);
    Optional<MyUserDetails> findByToken(String token);
    Optional<User> findById(Long id);
}
