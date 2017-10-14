package hello.services;

import hello.models.dbmodel.UserAccount;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * Created by yangyu on 27/9/17.
 */
public interface UserAccountService {
    Pair<Boolean, String> registerUser(String userName, String email, String password);
    UserAccount getUserByUserName(String userName);
    UserAccount getUserByEmail(String email);
}
