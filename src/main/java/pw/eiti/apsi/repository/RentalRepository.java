package pw.eiti.apsi.repository;

import org.springframework.data.repository.CrudRepository;
import pw.eiti.apsi.model.Rental;

public interface RentalRepository extends CrudRepository<Rental, Long> {
}
