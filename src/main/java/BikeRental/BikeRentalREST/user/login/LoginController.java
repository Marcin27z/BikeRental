package BikeRental.BikeRentalREST.user.login;

import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private User testUser = new User("ala@example.com", "ala", "ala", "563456345");

    @Autowired
    private UserService userService;

    @CrossOrigin()
    @GetMapping("/login-old")
    public User availableLogin(){
        return testUser;
    }

    @CrossOrigin()
    @PostMapping("/login-old")
    public boolean doLogin(@RequestBody User login){
        return login.equals(testUser);
    }

    @CrossOrigin()
    @GetMapping(value = "/api/users/user/{id}",produces = "application/json")
    public User getUserDetail(@PathVariable Long id){
        return userService.findById(id).orElse(null);
    }
}
