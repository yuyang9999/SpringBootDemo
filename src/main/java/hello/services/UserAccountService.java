package hello.services;

import hello.models.UserAccount;

import java.util.List;

/**
 * Created by yangyu on 27/9/17.
 */
public interface UserAccountService {
    boolean registerUser(String userName, String email, String password);
    UserAccount getUserByUserName(String userName);
    UserAccount getUserByEmail(String email);
}
