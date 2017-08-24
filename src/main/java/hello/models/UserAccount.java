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
    @Email
    private String email;

    @NotNull
    @Size(min = 5, max = 25)
    private String password;


    public UserAccount() {}

    public UserAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
