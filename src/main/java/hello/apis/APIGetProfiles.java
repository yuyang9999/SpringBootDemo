package hello.apis;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangyu on 3/9/17.
 */
@RestController
public class APIGetProfiles {

    private static final String apiVersion = "";

    @RequestMapping("/profiles")
    public String[] getProfiles() {
        return new String[]{"technology", "finance"};
    }

    @RequestMapping("/profile_symbols")
    public String[] getProfileSymbols(@RequestParam(value = "pname") String pname) {
        return new String[]{"AAPL", "NTES"};
    }

    @RequestMapping("/profile_add")
    public boolean addProfileSymbol(@RequestParam(value = "pname") String pname,  @RequestParam(value = "sname") String sname) {
        return true;
    }

}
