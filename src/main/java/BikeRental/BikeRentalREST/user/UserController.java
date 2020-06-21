package BikeRental.BikeRentalREST.user;

import BikeRental.BikeRentalREST.CustomMessage;
import BikeRental.BikeRentalREST.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/admin/users")
    public List<User> getUsers(@RequestParam(value = "active") final Optional<Boolean> activeUsers){
        if (activeUsers.isPresent()) {
            return userService.getUsers(activeUsers.get());
        } else {
            return userService.getUsers();
        }
    }

    @PostMapping("/admin/users/deactivate/{id}")
    public CustomMessage deactivateUser(@PathVariable final Long id){
        Optional<User> user = userService.deactivateUser(id);
        if (user.isPresent()){
            return new CustomMessage(1, "User deactivated.");
        } else {
            return new CustomMessage(0, "User does not exist.");
        }
    }

    @PostMapping("/admin/users/permissions/{id}")
    public CustomMessage changePermissions(@PathVariable final Long id, @RequestParam("isAdmin") final boolean isAdmin){
        Optional<User> user = userService.changePermissions(id, isAdmin);
        if (user.isPresent()){
            return new CustomMessage(1, "User permissions changed.");
        } else {
            return new CustomMessage(0, "User does not exist.");
        }
    }

    @CrossOrigin()
    @GetMapping("/api/users/currentUser")
    public UserDto getCurrentUser(@RequestHeader(name="Authorization") String token) {
        Optional<User> user = userService.findUserByToken(token);
        return user.map(UserDto::new).orElse(null);
    }
}
