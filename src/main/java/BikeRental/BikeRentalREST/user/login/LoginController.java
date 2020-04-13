package BikeRental.BikeRentalREST.user.login;

import BikeRental.BikeRentalREST.user.User;
import BikeRental.BikeRentalREST.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private Login testLogin = new Login(123L, "ala", "kot");

    @Autowired
    private UserService userService;

    @GetMapping("/login-old")
    public Login availableLogin(){
        return testLogin;
    }

    @PostMapping("/login-old")
    public boolean dologin(@RequestBody Login login){
        if(login.equals(this.testLogin))
            return true;
        else
            return false;
    }

    @GetMapping(value = "/api/users/user/{id}",produces = "application/json")
    public User getUserDetail(@PathVariable Long id){
        return userService.findById(id);
    }
}
