package hello.services.impl;

import hello.models.dbmodel.UserAccount;
import hello.models.dbmodel.UserRole;
import hello.models.inter.UserAccountMapper;
import hello.models.inter.UserRoleMapper;
import hello.services.UserAccountService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
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
    public Pair<Boolean, String> registerUser(String userName, String email, String password) {
        //sanity check
        if (userName == null || userName.length() == 0) {
            return new ImmutablePair<Boolean, String>(false, "user name is not valid");
        }

        if (email == null || email.length() == 0) {
            return new ImmutablePair<Boolean, String>(false, "email is not valid");
        }

        if (password == null || password.length() < 5) {
            return new ImmutablePair<Boolean, String>(false, "password is two short");
        }

        if (isUserExists(userName, email)) {
            return new ImmutablePair<Boolean, String>(false, "user with same name already exists");
        }

        UserAccount account = new UserAccount();
        account.setEmail(email);
        account.setUsername(userName);
        account.setEnabled((byte)1);
        account.setPassword(password);

        int ret = userAccountMapper.insertSelective(account);

        if (ret != 1) {
            return new ImmutablePair<Boolean, String>(false, "create user failed");
        }

        //also register user role
        UserRole userRole = new UserRole();
        userRole.setUsername(userName);
        userRole.setRole("USER");

        ret = userRoleMapper.insertSelective(userRole);

        if (ret != 1) {
            return new ImmutablePair<Boolean, String>(false, "create user failed");
        }

        return new ImmutablePair<Boolean, String>(true, "");
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
