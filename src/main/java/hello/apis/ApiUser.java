package hello.apis;

import hello.models.inter.UserAccountMapper;
import hello.models.inter.UserRoleMapper;
import hello.services.UserAccountService;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by yangyu on 7/10/17.
 */

@Data
class RegisterUserInput {
    @NotNull
    @Size(min = 2, max = 15)
    private String userName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 5, max = 25)
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}


@RestController
public class ApiUser {
    @Autowired
    UserAccountService userAccountService;

    @RequestMapping("/users/register")
    ApiResponse registerUser(@Valid RegisterUserInput input,
                                     Errors errors) {
        if (errors.hasErrors()) {
            List<ObjectError> errs = errors.getAllErrors();
            ObjectError firstError= errs.get(0);
            System.out.println(firstError.getDefaultMessage());

            return new ApiResponse(true, ((FieldError)firstError).getField() + ":" + firstError.getDefaultMessage(), false);
        }

        Pair<Boolean, String> ret = userAccountService.registerUser(input.getUserName(), input.getEmail(), input.getPassword());
        if (!ret.getLeft()) {
            return new ApiResponse(false, ret.getRight(), "");
        }

        return new ApiResponse(false, "", true);
    }

}
