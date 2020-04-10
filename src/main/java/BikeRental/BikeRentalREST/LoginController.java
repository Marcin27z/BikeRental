package BikeRental.BikeRentalREST;

import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private Login testLogin = new Login("ala", "kot");

    @GetMapping("/login")
    public Login availableLogin(){
        return testLogin;
    }

    @PostMapping("/login")
    public boolean dologin(@RequestBody Login login){
        if(login.equals(this.testLogin))
            return true;
        else
            return false;
    }
}
