package BikeRental.BikeRentalREST.user.service;

import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public String login(String username, String password) {
        Optional<User> user = userRepository.login(username,password);
        if(user.isPresent()){
            String token = UUID.randomUUID().toString();
            User userSec = user.get();
            userSec.getLogin().setToken(token);
            userRepository.save(userSec);
            return token;
        }

        return StringUtils.EMPTY;
    }

    @Override
    public Optional<org.springframework.security.core.userdetails.User> findByToken(String token) {
        Optional<User> user= userRepository.findByToken(token);
        if(user.isPresent()){
            User user1 = user.get();
            org.springframework.security.core.userdetails.User userSec=
                    new org.springframework.security.core.userdetails.User(user1.getLogin().getUsername(), user1.getLogin().getPassword(),
                            true, true, true, true,
                            AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(userSec);
        }
        return  Optional.empty();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user= userRepository.findById(id);
        return user.orElse(null);
    }
}