package BikeRental.BikeRentalREST.user.register;
import BikeRental.BikeRentalREST.CustomMessage;
import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RegisterController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public CustomMessage registerUser(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new CustomMessage(0, "This mail is taken");
        } else if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            return new CustomMessage(0, "This login is taken");
        } else if (userRepository.findByPhoneNumber((user.getPhoneNumber())).isPresent()) {
            return new CustomMessage(0, "This phone number is taken");
        } else {
            userRepository.save(user);
            return new CustomMessage(1, "User created!");
        }
    }
}
