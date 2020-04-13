package BikeRental.BikeRentalREST.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    // TODO need refactoring
    @Query(value = "SELECT u, l FROM User u, Login l where l.login = ?1 and l.password = ?2 ")
    Optional login(String username,String password);
    Optional findByToken(String token);
}
