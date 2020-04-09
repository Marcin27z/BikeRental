package pw.eiti.apsi.repository;

import org.springframework.data.repository.CrudRepository;
import pw.eiti.apsi.model.Station;

public interface StationRepository extends CrudRepository<Station, Long> {
}
