package BikeRental.BikeRentalREST.user.service;

import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserService {
    String login(String username, String password);
    Optional<User> findByToken(String token);
    //TODO change class name User to Customer
    BikeRental.BikeRentalREST.user.User findById(Long id);
}
