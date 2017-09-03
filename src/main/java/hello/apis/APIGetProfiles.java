package hello.apis;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangyu on 3/9/17.
 */
@RestController
public class APIGetProfiles {

    private static final String apiVersion = "/api";

    @RequestMapping(apiVersion + "/profiles")
    public String[] getProfiles() {
        String[] ret =  new String[]{"technology", "finance"};
        return ret;
    }

    @RequestMapping(apiVersion + "/profile_symbols")
    public String[] getProfileSymbols(@RequestParam(value = "pname") String pname) {
        return new String[]{"AAPL", "NTES"};
    }

    @RequestMapping(apiVersion + "/profile_add")
    public boolean addProfileSymbol(@RequestParam(value = "pname") String pname,  @RequestParam(value = "sname") String sname) {
        return true;
    }

}
