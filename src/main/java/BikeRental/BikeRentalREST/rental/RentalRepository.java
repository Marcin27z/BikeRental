package BikeRental.BikeRentalREST.rental;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends CrudRepository<Rental, Long> {
    List<Rental> findAll();
    @Query(value="select * from public.rentals where user_id = :UserId", nativeQuery = true)
    List<Rental> findByUserid(Long UserId);

    @Query(value="select * from public.rentals where user_id = :UserId and return_date is null", nativeQuery = true)
    Optional<Rental> findOpenRental(Long UserId);
}
