package hello.services.impl;

import hello.models.DataSourcesUtility;
//import hello.models.UserAccount;
import hello.models.dbmodel.UserAccount;
import hello.models.inter.ProfileMapper;
import hello.models.inter.UserAccountMapper;
import hello.services.UserAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Created by yangyu on 27/9/17.
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountMapper userAccountMapper;

    public UserAccountServiceImpl() {
        userAccountMapper = BatisMappers.userAccountMapper;
    }

    private boolean isUserExists(String userName, String email) {

        return false;
    }

    /**
     * make sure to call isUserExists before call this method
     * @param userName user name
     * @param email    email address
     * @param password password
     * @return true if succeed or false if failed
     */
    private boolean insertUser(String userName, String email, String password) {
        return false;
    }

    @Override
    public boolean registerUser(String userName, String email, String password) {
        //sanity check
        if (userName == null || userName.length() == 0) {
            return false;
        }

        if (email == null || email.length() == 0) {
            return false;
        }

        if (password == null || password.length() < 5) {
            return false;
        }

        if (isUserExists(userName, email)) {
            return false;
        }

        UserAccount account = new UserAccount();
        account.setEmail(email);
        account.setUsername(userName);
        account.setEnabled((byte)1);
        account.setPassword(password);

        int ret = userAccountMapper.insertSelective(account);

        return ret == 1;
    }

    @Override
    public UserAccount getUserByUserName(String userName) {
        return userAccountMapper.selectByUserName(userName);
    }

    @Override
    public UserAccount getUserByEmail(String email) {
        return userAccountMapper.selectByEmail(email);
    }
}
