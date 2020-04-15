package BikeRental.BikeRentalREST.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query(value = "SELECT u FROM User u where u.email = ?1 or u.login = ?2")
    Optional<User> userExists(String email, String login);
    Optional<User> findByEmailOrLoginOrPhoneNumber(String email, String login, String phoneNumber);
    Optional<User> findByEmail(String email);
    Optional<User> findByLogin(String login);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
