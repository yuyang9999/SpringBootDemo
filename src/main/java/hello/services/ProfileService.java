package hello.services;

import hello.models.dbmodel.UserAccount;
import hello.models.dbmodel.Profile;

import java.util.List;

/**
 * Created by yangyu on 2/10/17.
 */
public interface ProfileService {
    List<Profile> getProfilesForUser(UserAccount account);

    boolean createNewProfileForUser(UserAccount account, String profileName);
    boolean deleteProfile(Profile profile);
    Profile getUserProfileWithName(UserAccount account, String profileName);
    boolean updateProfileName(Profile profile, String newProfileName);
}
