package BikeRental.BikeRentalREST.rental;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RentalRepository extends CrudRepository<Rental, Long> {
    List<Rental> findAll();
    @Query(value="select * from public.rentals where user_id = :UserId", nativeQuery = true)
    List<Rental> findByUserid(Long UserId);
}
