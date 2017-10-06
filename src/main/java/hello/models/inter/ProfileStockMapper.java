package hello.models.inter;

import hello.models.dbmodel.ProfileStock;

import java.util.List;

public interface ProfileStockMapper {
    int deleteByPrimaryKey(Integer sid);

    int insert(ProfileStock record);

    int insertSelective(ProfileStock record);

    ProfileStock selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(ProfileStock record);

    int updateByPrimaryKey(ProfileStock record);

    List<ProfileStock> selectByProfileId(Integer pid);

    List<ProfileStock> selectByProfileIdAndStockName(Integer pid, String stockName);
}