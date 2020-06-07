package BikeRental.BikeRentalREST.user;

public class UserDto {

    private String login;

    private String email;

    private String phoneNumber;

    public UserDto(User user) {
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLogin() {
        return login;
    }
}
