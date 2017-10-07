package hello.services.impl;

import hello.models.dbmodel.UserAccount;
import hello.models.inter.UserAccountMapper;
import hello.models.inter.UserRoleMapper;
import hello.services.UserAccountService;
import org.springframework.stereotype.Service;

/**
 * Created by yangyu on 27/9/17.
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountMapper userAccountMapper;

    private UserRoleMapper userRoleMapper;

    public UserAccountServiceImpl() {
        userAccountMapper = BatisMappers.userAccountMapper;
        userRoleMapper = BatisMappers.userRoleMapper;
    }

    private boolean isUserExists(String userName, String email) {
        UserAccount userAccount = userAccountMapper.selectByUserName(userName);
        if (userAccount != null) {
            return true;
        }

        userAccount = userAccountMapper.selectByEmail(email);

        return userAccount != null;
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

        //also register user


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
