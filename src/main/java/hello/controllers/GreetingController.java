package hello.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by yangyu on 23/8/17.
 */
@Controller
@RequestMapping("/")
public class GreetingController {

    @RequestMapping(method = RequestMethod.GET)
    public String greeting() {
        return "index";
    }
}
