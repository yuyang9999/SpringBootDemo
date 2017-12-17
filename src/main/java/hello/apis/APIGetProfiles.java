package hello.apis;

import hello.models.dbmodel.Profile;
import hello.models.dbmodel.ProfileStock;
import hello.models.dbmodel.StockHistory;
import hello.models.dbmodel.UserAccount;
import hello.services.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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


    static private final ApiResponse userNotExistError = new ApiResponse(true, "user is not existed", "");
    static private final ApiResponse nameNotValidError = new ApiResponse(true, "user is not existed", "");




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
    public ApiResponse getProfiles() {
        List<Profile> ret = new ArrayList<Profile>();

        UserAccount userAccount = getCurrentUserAccount();
        if (userAccount == null) {
            return new ApiResponse(true, "user account is not existed", "");
        }

        ret = profileService.getProfilesForUser(userAccount);

        return new ApiResponse(false, "", ret);
    }

    @RequestMapping(value = apiVersion + "/profile_delete", method = RequestMethod.POST)
    public ApiResponse deleteOneProfile(@RequestParam(value = "pname") String pname) {
        if (StringUtils.isBlank(pname)) {
            return new ApiResponse(true, "profile name is not valid", "");
        }

        UserAccount userAccount = getCurrentUserAccount();
        if (userAccount == null) {
            return new ApiResponse(true, "user account is not valid", "");
        }

        Profile profile = profileService.getUserProfileWithName(userAccount, pname);
        if (profile == null) {
            return new ApiResponse(true, "profile is not existed", "");
        }

        if (profileService.deleteProfile(profile)) {
            return new ApiResponse(false, "", true);
        } else {
            return new ApiResponse(true, "delete profile failed", "");
        }
    }

    @RequestMapping(apiVersion + "/profile_add")
    public ApiResponse addOneProfile(@RequestParam(value = "pname") String pname) {
        if (StringUtils.isBlank(pname)) {
            return new ApiResponse(true, "profile name is not valid", "");
        }

        UserAccount userAccount = getCurrentUserAccount();
        if (userAccount == null) {
            return new ApiResponse(true, "user account is not valid", "");
        }

        //could not create a profile with same name as existing profile name
        Profile profile = profileService.getUserProfileWithName(userAccount, pname);
        if (profile != null) {
            return new ApiResponse(true, "the profile with the same name already exists", "");
        }

        if (profileService.createNewProfileForUser(userAccount, pname)) {
            return new ApiResponse(false, "", true);
        } else {
            return new ApiResponse(true, "create profile failed", "");
        }
    }

    @RequestMapping(apiVersion + "/profile_symbols")
    public ApiResponse getProfileSymbols(@RequestParam(value = "pname") String pname) {
        List<ProfileStock> ret = new ArrayList<ProfileStock>();

        if (StringUtils.isBlank(pname)) {
            return new ApiResponse(true,"profile name is not valid", ret);
        }

        UserAccount user = getCurrentUserAccount();
        if (user == null) {
            return new ApiResponse(true, "user not existed", ret);
        }

        Profile profile = profileService.getUserProfileWithName(user, pname);
        if (profile == null) {
            return new ApiResponse(true, "profile not existed", ret);
        }

        List<ProfileStock> stocks = stockService.getProfileStocks(profile);

        //return the latest day's stock data if exists
//        Date d1 = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -15);
//        Date d2 = cal.getTime();
//        Map<String, StockHistory> stockData = new HashMap<String, StockHistory>();
//        for (ProfileStock p: stocks) {
//            String symbol = p.getSname();
//            List<StockHistory> sh = historyService.getStockHistoriesWithSymbolNameAndDateRange(symbol, d2, d1);
//            if (sh.size() > 0) {
//                stockData.put(symbol, sh.get(0));
//            }
//        }
//
//        List<Object> retObject = new ArrayList<Object>();
//        retObject.add(stocks);
//        retObject.add(stockData);
//
//
//        if (stocks == null) {
//            return new ApiResponse("can't find stocks");
//        }

        return new ApiResponse(stocks);
    }


    @RequestMapping(apiVersion + "/profile_only_add_symbol")
    public ApiResponse addProfileOnlySymbol(@RequestParam(value = "pname") String pname, @RequestParam(value = "sname") String sname) {
        if (StringUtils.isBlank(pname) || StringUtils.isBlank(sname)) {
            return nameNotValidError;
        }

        //check stock name is valid
        if (!symbolService.isSymbolExists(sname)) {
            return new ApiResponse("stock name is not valid");
        }

        UserAccount user = getCurrentUserAccount();
        if (user == null) {
            return userNotExistError;
        }

        Profile profile =  profileService.getUserProfileWithName(user, pname);
        if (profile == null) {
            return new ApiResponse("profile not existed");
        }

        boolean succeed = stockService.createNewStock(profile, sname, 0, 0, new Date());
        if (!succeed) {
            return new ApiResponse("can't add new symbol");
        }

        return ApiResponse.succeedResp;
    }


    @RequestMapping(apiVersion + "/profile_symbol_add")
    public ApiResponse addProfileSymbol(@RequestParam(value = "pname") String pname,  @RequestParam(value = "sname") String sname, @RequestParam(value = "share") Integer share, @RequestParam(value = "price") Float price,  @RequestParam(value = "bought_date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        if (StringUtils.isBlank(sname) || StringUtils.isBlank(pname)) {
            return nameNotValidError;
        }

        //check stock name is valid
        if (!symbolService.isSymbolExists(sname)) {
            return new ApiResponse("stock name is not valid");
        }

        UserAccount user = getCurrentUserAccount();
        if (user == null) {
            return userNotExistError;
        }

        Profile profile = profileService.getUserProfileWithName(user, pname);
        if (profile == null) {
            return new ApiResponse("profile name not existed");
        }

        boolean succeed =  stockService.createNewStock(profile, sname, price, share, date);
        if (!succeed) {
            return new ApiResponse("can't add new symbol");
        }

        return new ApiResponse(false, "", "");
    }

    @RequestMapping(value = apiVersion + "/profile_symbol_delete", method = RequestMethod.POST)
    public ApiResponse deleteProfileSymbol(@RequestParam(value = "pname") String pname, @RequestParam(value = "profile_stock_id") Integer profile_stock_id) {
        if (StringUtils.isBlank(pname) || profile_stock_id == null) {
            return nameNotValidError;
        }

        UserAccount user = getCurrentUserAccount();
        if (user == null) {
            return userNotExistError;
        }

        Profile profile = profileService.getUserProfileWithName(user, pname);
        if (profile == null) {
            return new ApiResponse("profile is not valid");
        }

        ProfileStock profileStock = stockService.getProfileStockWithPrimaryId(profile_stock_id);
        if (profileStock == null || profileStock.getPid() != profile.getPid()) {
            return new ApiResponse("stock is not valid");
        }

        boolean succeed =  stockService.deleteOneStock(profileStock);

        if (!succeed) {
            return new ApiResponse("delete failed");
        }

        return new ApiResponse(false, "",       "");
    }

}
