package hello.services.impl;

import hello.models.dbmodel.Profile;
import hello.models.dbmodel.UserAccount;
import hello.models.inter.ProfileMapper;
import hello.models.inter.ProfileStockMapper;
import hello.services.ProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangyu on 3/10/17.
 */
@Service
public class ProfileServiceImpl implements ProfileService {
    private ProfileMapper profileMapper;
    private ProfileStockMapper profileStockMapper;

    public ProfileServiceImpl() {
        profileMapper = BatisMappers.profileMapper;
        profileStockMapper = BatisMappers.profileStockMapper;
    }

    @Override
    public List<Profile> getProfilesForUser(UserAccount account) {
        return profileMapper.selectByUserId(account.getUserId());
    }

    @Override
    public boolean createNewProfileForUser(UserAccount account, String profileName) {
        Profile profile = new Profile();
        profile.setUserId(account.getUserId());
        profile.setPname(profileName);

        int insertCnt = profileMapper.insertSelective(profile);
        return insertCnt == 1;
    }

    @Override
    public boolean deleteProfile(Profile profile) {
        profileStockMapper.deleteByPid(profile.getPid());

        Integer deleteNum = profileMapper.deleteByPrimaryKey(profile.getPid());
        return deleteNum == 1;
    }

    @Override
    public Profile getUserProfileWithName(UserAccount account, String profileName) {
        return profileMapper.selectByUserIdAndProfileName(account.getUserId(), profileName);
    }

    @Override
    public boolean updateProfileName(Profile profile, String newProfileName) {
        profile.setPname(newProfileName);
        int ret = profileMapper.updateByPrimaryKeySelective(profile);

        return ret == 1;
    }
}
