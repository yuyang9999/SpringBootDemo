package hello.services.impl;

import hello.models.DataSourcesUtility;
import hello.models.UserAccount;
import hello.models.UserProfile;
import hello.services.ProfileService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by yangyu on 3/10/17.
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    public ProfileServiceImpl() {
    }

    @Override
    public List<UserProfile> getProfilesForUser(UserAccount account) {



        return null;
    }

    @Override
    public boolean createNewProfileForUser(UserAccount account) {

        return false;
    }

    @Override
    public boolean deleteProfileForUser(UserAccount account, UserProfile profile) {
        return false;
    }

    @Override
    public UserProfile getUserProfileWithName(UserAccount account, String profileName) {
        return null;
    }

    @Override
    public boolean updateProfileName(UserProfile profile, String newProfileName) {
        return false;
    }
}
