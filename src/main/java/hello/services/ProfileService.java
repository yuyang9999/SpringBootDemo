package hello.services;

import hello.models.UserAccount;
import hello.models.UserProfile;

import java.util.List;

/**
 * Created by yangyu on 2/10/17.
 */
public interface ProfileService {
    List<UserProfile> getProfilesForUser(UserAccount account);

    boolean createNewProfileForUser(UserAccount account);
    boolean deleteProfileForUser(UserAccount account, UserProfile profile);
    UserProfile getUserProfileWithName(UserAccount account, String profileName);
    boolean updateProfileName(UserProfile profile, String newProfileName);
}
