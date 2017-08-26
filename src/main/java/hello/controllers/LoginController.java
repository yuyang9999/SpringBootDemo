package hello.controllers;

import hello.models.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;

/**
 * Created by yangyu on 26/8/17.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private DataSource dataSource;

    @RequestMapping(method = RequestMethod.GET)
    String getLogIn() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    String processLogInRequest(UserAccount user, Model model) {
        String email = user.getEmail();
        String password = user.getPassword();



        model.addAttribute("name", email);
        return "greeting";
    }

}
