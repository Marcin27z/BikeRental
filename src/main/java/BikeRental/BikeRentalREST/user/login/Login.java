package BikeRental.BikeRentalREST.user.login;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Login")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loginId;

    private String userName;
    private String password;

    private String token;

    public Login(){ }

    public Login(String login, String password){
        this.userName = login;
        this.password = password;
    }

    public Login(Long loginId, String login, String password){
        this.loginId = loginId;
        this.userName = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login1 = (Login) o;
        return userName.equals(login1.userName) &&
                password.equals(login1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Override
    public String toString() {
        return "Login{" +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
