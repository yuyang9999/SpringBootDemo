package hello;

import hello.models.UserAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

/**
 * Created by yangyu on 23/8/17.
 */
@Controller
@RequestMapping("user/")
@SessionAttributes("userId")
public class RegisterController {

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterForm() {
        return "register";
    }

    @RequestMapping(value = "/register", method=RequestMethod.POST)
    public String processRegisteration(@Valid UserAccount user, Errors errors, Model model) throws  Exception {
        if (errors.hasErrors()) {
            return "register";
        }

        model.addAttribute("userId", 3);

        System.out.println(user.getEmail());
        System.out.printf(user.getPassword());

        return "redirect:/user/check";
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

}
