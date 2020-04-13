package BikeRental.BikeRentalREST.user.login;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Login")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loginId;

    private String username;
    private String password;

    private String token;

    public Login(Long loginId, String login, String password){
        this.loginId = loginId;
        this.username = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login1 = (Login) o;
        return username.equals(login1.username) &&
                password.equals(login1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
