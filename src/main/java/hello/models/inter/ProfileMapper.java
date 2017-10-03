package hello.models.inter;

import hello.models.dbmodel.Profile;

import java.util.List;

public interface ProfileMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(Profile record);

    int insertSelective(Profile record);

    Profile selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(Profile record);

    int updateByPrimaryKey(Profile record);

    List<Profile> selectByUserId(Integer userId);

    Profile selectByUserIdAndProfileName(Integer userId, String pname);
}