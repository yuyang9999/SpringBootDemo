package hello.services;

import hello.models.dbmodel.Profile;
import hello.models.dbmodel.ProfileStock;

import java.util.Date;
import java.util.List;

/**
 * Created by yangyu on 2/10/17.
 */
public interface StockService {
    List<ProfileStock> getProfileStocks(Profile profile);
    boolean createNewStock(Profile profile, String symbol, float price, int share, Date boughtDate);
    boolean deleteOneStock(ProfileStock stock);
    List<ProfileStock> getProfileStockWithName(Profile profile, String stockName);
    ProfileStock getProfileStockWithPrimaryId(Integer id);

    boolean updateStockPrice(ProfileStock stock, float price);
    boolean updateStockShare(ProfileStock stock, int share);
    boolean updateStockBoughtDate(ProfileStock stock, Date newDate);
}
