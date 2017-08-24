package hello;

import hello.models.UserAccount;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URLEncoder;

/**
 * Created by yangyu on 23/8/17.
 */
@Controller
public class RegisterController {

    @RequestMapping(name = "/register", method = RequestMethod.GET)
    public String showRegisterForm() {
        return "register";
    }

    @RequestMapping(name = "/register", method=RequestMethod.POST)
    public String processRegisteration(@Valid UserAccount user, Errors errors) throws  Exception {
        if (errors.hasErrors()) {
            return "register";
        }

        System.out.println(user.getEmail());
        System.out.printf(user.getPassword());

        return "redirect:/greeting?name=" + URLEncoder.encode(user.getEmail(), "UTF-8");
    }


}
