package BikeRental.BikeRentalREST.user.login;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends CrudRepository<Login, Long> {
    @Query(value = "SELECT l FROM Login l where l.userName = ?1 and l.password = ?2 ")
    Optional<Login> login(String username, String password);
    Optional<Login> findByToken(String token);
}
