package pw.eiti.apsi.repository;

import org.springframework.data.repository.CrudRepository;
import pw.eiti.apsi.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
