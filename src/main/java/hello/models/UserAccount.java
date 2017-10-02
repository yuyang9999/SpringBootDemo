package hello.models;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by yangyu on 24/8/17.
 */
@Data
public class UserAccount {
    @NotNull
    @Size (min = 2, max = 15)
    private String userName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 5, max = 25)
    private String password;

    private int userId;

    public UserAccount(int userId, String userName, String email, String password) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.userId = userId;
    }

    public String getUserName() {return userName;}

    public String getEmail() {return email;}

    public String getPassword() {return password;}

    public int getUserId() {return userId;}
}
