package hello.services;

import hello.models.UserProfile;
import hello.models.UserStock;

import java.util.Date;
import java.util.List;

/**
 * Created by yangyu on 2/10/17.
 */
public interface StockService {
    List<UserStock> getProfileStocks(UserProfile profile);
    boolean createNewStock(UserProfile profile, String symbol, float price, float share, Date boughtDate);
    boolean deleteOneStock(UserStock stock);

    boolean updateStockPrice(UserStock stock, float price);
    boolean updateStockShare(UserStock stock, float share);
    boolean updateStockBoughtDate(UserStock stock, Date newDate);
}
