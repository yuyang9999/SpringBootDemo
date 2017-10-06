package hello.apis;

import hello.models.dbmodel.Profile;
import hello.models.dbmodel.ProfileStock;
import hello.models.dbmodel.UserAccount;
import hello.services.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private ProfileService profileService;


    private UserAccount getCurrentUserAccount() {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        if (contextHolder == null) {
            return null;
        }

        User user = (User)contextHolder.getPrincipal();
        if (user == null) {
            return null;
        }

        String userName = user.getUsername();
        if (StringUtils.isBlank(userName)) {
            return null;
        }

        UserAccount ret = accountService.getUserByUserName(userName);
        return ret;
    }

    @RequestMapping(apiVersion + "/profiles")
    public List<Profile> getProfiles() {
        List<Profile> ret = new ArrayList<Profile>();

        UserAccount userAccount = getCurrentUserAccount();
        if (userAccount == null) {
            return ret;
        }

        ret = profileService.getProfilesForUser(userAccount);
        return ret;
    }

    @RequestMapping(value = apiVersion + "/profile_delete", method = RequestMethod.POST)
    public boolean deleteOneProfile(@RequestParam(value = "pname") String pname) {
        if (StringUtils.isBlank(pname)) {
            return false;
        }

        UserAccount userAccount = getCurrentUserAccount();
        if (userAccount == null) {
            return false;
        }

        Profile profile = profileService.getUserProfileWithName(userAccount, pname);
        if (profile == null) {
            return false;
        }

        return profileService.deleteProfile(profile);
    }

    @RequestMapping(apiVersion + "/profile_add")
    public boolean addOneProfile(@RequestParam(value = "pname") String pname) {
        if (StringUtils.isBlank(pname)) {
            return false;
        }

        UserAccount userAccount = getCurrentUserAccount();
        if (userAccount == null) {
            return false;
        }

        //could not create a profile with same name as existing profile name
        Profile profile = profileService.getUserProfileWithName(userAccount, pname);
        if (profile != null) {
            return false;
        }

        return profileService.createNewProfileForUser(userAccount, pname);
    }

    @RequestMapping(apiVersion + "/profile_symbols")
    public List<ProfileStock> getProfileSymbols(@RequestParam(value = "pname") String pname) {
        List<ProfileStock> ret = new ArrayList<ProfileStock>();

        if (StringUtils.isBlank(pname)) {
            return ret;
        }

        UserAccount user = getCurrentUserAccount();
        if (user == null) {
            return ret;
        }

        Profile profile = profileService.getUserProfileWithName(user, pname);
        if (profile == null) {
            return ret;
        }

        List<ProfileStock> stocks = stockService.getProfileStocks(profile);
        if (stocks == null) {
            return ret;
        }

        return stocks;
    }

    @RequestMapping(apiVersion + "/profile_symbol_add")
    public boolean addProfileSymbol(@RequestParam(value = "pname") String pname,  @RequestParam(value = "sname") String sname, @RequestParam(value = "share") Integer share, @RequestParam(value = "price") Float price,  @RequestParam(value = "bought_date") Date date) {
        if (StringUtils.isBlank(sname) || StringUtils.isBlank(pname)) {
            return false;
        }

        //check stock name is valid
        if (!symbolService.isSymbolExists(sname)) {
            return false;
        }

        UserAccount user = getCurrentUserAccount();
        if (user == null) {
            return false;
        }

        Profile profile = profileService.getUserProfileWithName(user, pname);
        if (profile == null) {
            return false;
        }

        return stockService.createNewStock(profile, sname, price, share, date);
    }

    @RequestMapping(value = apiVersion + "/profile_symbol_delete", method = RequestMethod.POST)
    public boolean deleteProfileSymbol(@RequestParam(value = "pname") String pname, @RequestParam(value = "profile_stock_id") Integer profile_stock_id) {
        if (StringUtils.isBlank(pname) || profile_stock_id == null) {
            return false;
        }

        UserAccount user = getCurrentUserAccount();
        if (user == null) {
            return false;
        }

        Profile profile = profileService.getUserProfileWithName(user, pname);
        if (profile == null) {
            return false;
        }

        ProfileStock profileStock = stockService.getProfileStockWithPrimaryId(profile_stock_id);
        if (profileStock == null || profileStock.getPid() != profile.getPid()) {
            return false;
        }

        return stockService.deleteOneStock(profileStock);
    }

}
