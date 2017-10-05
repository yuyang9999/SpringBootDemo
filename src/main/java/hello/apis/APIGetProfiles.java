package hello.apis;

import hello.models.StockLookup;
import hello.models.dbmodel.StockHistory;
import hello.services.StockHistoryService;
import hello.services.StockService;
import hello.services.StockSymbolService;
import hello.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Created by yangyu on 3/9/17.
 */
@RestController
public class APIGetProfiles {

    private static final String apiVersion = ApiConfig.apiVersion;

    @Autowired
    private UserAccountService accountService;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockHistoryService historyService;

    @Autowired
    private StockSymbolService symbolService;

    @RequestMapping(apiVersion + "/profiles")
    public String[] getProfiles(HttpSession session) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        Object crediential = contextHolder.getCredentials();

        String[] ret =  new String[]{"technology", "finance"};
        return ret;
    }

    @RequestMapping(value = apiVersion + "/profile_delete", method = RequestMethod.POST, consumes = {"application/json"})
    public boolean deleteOneProfile(@RequestBody String[] names) {
        System.out.println(names);
        return true;
    }

    @RequestMapping(apiVersion + "/profile_add")
    public boolean addOneProfile(@RequestParam(value = "pname") String pname) {
        if (StockLookup.checkStockSymbolValid(pname)) {
            return true;
        }

        return false;
    }

    @RequestMapping(apiVersion + "/profile_symbols")
    public String[] getProfileSymbols(@RequestParam(value = "pname") String pname) {
        return new String[]{"AAPL", "NTES"};
    }

    @RequestMapping(apiVersion + "/profile_symbol_add")
    public boolean addProfileSymbol(@RequestParam(value = "pname") String pname,  @RequestParam(value = "sname") String sname) {
        return true;
    }

    @RequestMapping(apiVersion + "/profile_symbol_delete")
    public boolean deleteProfileSymbol(@RequestParam(value = "pname") String pname, @RequestParam(value = "sname") String sname) {
        return true;
    }

}
