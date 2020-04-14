package BikeRental.BikeRentalREST.user.service;

import BikeRental.BikeRentalREST.user.login.Login;
import BikeRental.BikeRentalREST.user.login.LoginRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public String login(String username, String password) {
        //TODO uncomment after adding database with new data set for login repository
//        Optional<Login> login = loginRepository.login(username, password);
//        if(login.isPresent()){
//            String token = UUID.randomUUID().toString();
//            Login user = login.get();
//            user.setToken(token);
//            loginRepository.save(user);
//            return token;
//        }
        return StringUtils.EMPTY;
    }

    @Override
    public Optional<User> findByToken(String token) {
        Optional<Login> login = loginRepository.findByToken(token);
        if(login.isPresent()){
            Login login1 = login.get();
            User user = new User(login1.getUserName(), login1.getPassword(), true, true, true, true,
                            AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(user);
        }
        return  Optional.empty();
    }

    @Override
    public Login findById(Long id) {
        Optional<Login> login = loginRepository.findById(id);
        return login.orElse(null);
    }
}