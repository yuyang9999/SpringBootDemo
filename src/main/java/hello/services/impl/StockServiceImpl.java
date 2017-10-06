package hello.services.impl;

import hello.models.dbmodel.Profile;
import hello.models.dbmodel.ProfileStock;
import hello.models.inter.ProfileStockMapper;
import hello.services.StockService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangyu on 3/10/17.
 */
@Service
public class StockServiceImpl implements StockService {
    private ProfileStockMapper stockMapper;

    public StockServiceImpl() {
        stockMapper = BatisMappers.profileStockMapper;
    }

    @Override
    public List<ProfileStock> getProfileStocks(Profile profile) {
        return stockMapper.selectByProfileId(profile.getPid());
    }

    @Override
    public boolean createNewStock(Profile profile, String symbol, float price, int share, Date boughtDate) {
        if (profile == null) {
            return false;
        }

        ProfileStock profileStock = new ProfileStock();
        profileStock.setPid(profile.getPid());
        profileStock.setSname(symbol);
        profileStock.setPrice(price);
        profileStock.setShare(share);
        profileStock.setBoughtTime(boughtDate);
        profileStock.setUserId(profile.getUserId());

        int ret = stockMapper.insertSelective(profileStock);

        return ret == 1;
    }

    @Override
    public boolean deleteOneStock(ProfileStock stock) {
        int ret = stockMapper.deleteByPrimaryKey(stock.getSid());
        return ret == 1;
    }

    @Override
    public List<ProfileStock> getProfileStockWithName(Profile profile, String stockName) {
        return stockMapper.selectByProfileIdAndStockName(profile.getPid(), stockName);
    }

    @Override
    public ProfileStock getProfileStockWithPrimaryId(Integer id) {
        return stockMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateStockPrice(ProfileStock stock, float price) {
        stock.setPrice(price);
        int update = stockMapper.updateByPrimaryKeySelective(stock);
        return update == 1;
    }

    @Override
    public boolean updateStockShare(ProfileStock stock, int share) {
        stock.setShare(share);
        int update = stockMapper.updateByPrimaryKeySelective(stock);

        return update == 1;
    }

    @Override
    public boolean updateStockBoughtDate(ProfileStock stock, Date newDate) {
        stock.setBoughtTime(newDate);
        int update = stockMapper.updateByPrimaryKeySelective(stock);

        return update == 1;
    }
}
