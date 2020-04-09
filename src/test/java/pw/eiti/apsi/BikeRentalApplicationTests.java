package pw.eiti.apsi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pw.eiti.apsi.model.User;
import pw.eiti.apsi.repository.UserRepository;

@SpringBootTest
class BikeRentalApplicationTests {

	@Autowired
	public UserRepository userRepository;

	@Test
	void contextLoads() {
		User user = new User();
		user.setEmail("example@example.com");
		userRepository.save(user);
	}

}
