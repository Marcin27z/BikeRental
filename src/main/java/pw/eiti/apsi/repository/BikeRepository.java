package pw.eiti.apsi.repository;

import org.springframework.data.repository.CrudRepository;
import pw.eiti.apsi.model.Bike;

public interface BikeRepository extends CrudRepository<Bike, Long> {
}
