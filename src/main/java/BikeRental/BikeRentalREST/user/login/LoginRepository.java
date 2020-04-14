package BikeRental.BikeRentalREST.user.login;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LoginRepository extends CrudRepository<Login, Long> {
    //TODO uncomment after adding database with new data set for login repository
//    @Query(value = "SELECT l FROM Login l where l.username = ?1 and l.password = ?2 ")
//    Optional<Login> login(String username, String password);
    Optional<Login> findByToken(String token);
}
