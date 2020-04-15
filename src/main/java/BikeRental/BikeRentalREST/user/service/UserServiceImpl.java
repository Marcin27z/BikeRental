package BikeRental.BikeRentalREST.user.service;

import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.UserRepository;
import BikeRental.BikeRentalREST.user.security.MyUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(String username, String password) {
        Optional<User> user = userRepository.login(username, password);
        return user.map(u -> {
            String token = UUID.randomUUID().toString();
            u.setToken(token);
            userRepository.save(u);
            return token;
        }).orElse(StringUtils.EMPTY);
    }

    @Override
    public Optional<MyUserDetails> findByToken(String token) {
        Optional<User> user = userRepository.findByToken(token);
        return user.map(MyUserDetails::new);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}