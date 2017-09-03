package hello.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yangyu on 3/9/17.
 */

@Controller
@RequestMapping("/profiles")
public class ProfilesController {

    @RequestMapping(method = RequestMethod.GET)
    public String profiles() {
        return "profiles";
    }
}
