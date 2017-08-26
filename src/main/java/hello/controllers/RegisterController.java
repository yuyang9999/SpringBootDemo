package hello.controllers;

import hello.models.UserAccount;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.Valid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;




/**
 * Created by yangyu on 23/8/17.
 */
@Controller
@RequestMapping("user/")
//@SessionAttributes("userId")
public class RegisterController {
    private final String createUserSql = "insert into users (username, email, password) values (?,?,?)";
    private final String createUserRole = "insert into user_roles (username, role) values (?, 'ROLE_USER')";

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterForm() {
        return "register";
    }

    @RequestMapping(value = "/register", method=RequestMethod.POST)
    public String processRegisteration(@Valid UserAccount user, Errors errors, Model model, HttpServletRequest request) throws  Exception {
        if (errors.hasErrors()) {
            return "register";
        }

        String userName = user.getUserName();
        String email = user.getEmail();
        String password = user.getPassword();

        Connection conn = null;
        PreparedStatement stmt = null;
        boolean succeed = true;
        try {
            conn = dataSource.getConnection();

            stmt = conn.prepareStatement(createUserSql);
            stmt.setString(1, userName);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.execute();

            stmt = conn.prepareStatement(createUserRole);
            stmt.setString(1, userName);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            succeed = false;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        if (succeed) {
            authenticateUserAndSetSession(user, request);
            return "redirect:/user/check";
        }

        return "register";
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String checkRegisteration(Model model, HttpServletRequest request, HttpSession session) {
        Map modelMap = model.asMap();
        for (Object modelKey: modelMap.keySet()) {
            Object modelValue = modelMap.get(modelKey);
            System.out.println(modelKey + " -- " + modelValue);
        }

        model.addAttribute("name", "world");

        return "greeting";
    }

    private void authenticateUserAndSetSession(UserAccount userAccount, HttpServletRequest request) {
        String userName = userAccount.getUserName();
        String password = userAccount.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

}
