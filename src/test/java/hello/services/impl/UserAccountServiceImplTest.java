package hello.services.impl;

import hello.Application;
import hello.models.dbmodel.Profile;
import hello.models.dbmodel.ProfileStock;
import hello.models.dbmodel.StockSymbol;
import hello.models.dbmodel.UserAccount;
import hello.models.inter.ProfileStockMapper;
import hello.services.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by yangyu on 2/10/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class UserAccountServiceImplTest {
    @Autowired
    UserAccountService userAccountService;

    @Autowired
    ProfileService profileService;

    @Autowired
    StockService stockService;

    @Autowired
    StockSymbolService stockSymbolService;

    @Autowired
    StockHistoryService stockHistoryService;

    @Test
    public void testUserAccount() throws Exception {
        //create stock symbol
        StockSymbol applSymbol= stockSymbolService.addSymbol("AAPL","apple conpany", "tech", "tech");
        applSymbol = stockSymbolService.getSymbolBySName("AAPL");
        StockSymbol netsSymbol = stockSymbolService.addSymbol("NTES", "netease", "tech", "tech");

        //test user
        userAccountService.registerUser("test1", "test2", "test3");
        System.out.println(userAccountService.getUserByEmail("test2"));
        System.out.println(userAccountService.getUserByUserName("test1"));
        System.out.println(userAccountService.getUserByUserName("no name"));

        //test profile
        UserAccount userAccount = userAccountService.getUserByEmail("test2");
        profileService.createNewProfileForUser(userAccount, "profile1");
        profileService.createNewProfileForUser(userAccount, "profile2");

        List<Profile> profiles = profileService.getProfilesForUser(userAccount);
        System.out.println(profiles);

        //test stock
        Profile profile = profiles.get(0);


        //create stock
        stockService.createNewStock(profile, "AAPL", 150, 10, new Date());
        stockService.createNewStock(profile, "NTES", 265, 10, new Date());

        List<ProfileStock> stocks = stockService.getProfileStocks(profile);
        System.out.println(stocks);
    }


}