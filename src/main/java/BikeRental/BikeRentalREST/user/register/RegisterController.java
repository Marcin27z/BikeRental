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
    public CustomMessage registerUser(@Valid @RequestBody User user){
        if(userRepository.findByEmailOrLogin(user.getEmail(), user.getLogin()).isEmpty()){
            userRepository.save(user);
            return new CustomMessage(1, "User created!");
        }
        else
            return new CustomMessage(0, "User Already Exists!");
    }
}
