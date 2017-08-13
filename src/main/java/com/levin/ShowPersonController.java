package com.levin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yangyu on 13/8/17.
 */
@Controller
public class ShowPersonController {
    @RequestMapping("/showPerson")
    public @ResponseBody Person showPerson() {
        return new Person("levinzhang", "levinzhang1981@gmail.com");
    }
}
