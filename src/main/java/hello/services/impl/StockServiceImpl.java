package hello.services.impl;

import hello.models.DataSourcesUtility;
import hello.models.UserProfile;
import hello.models.UserStock;
import hello.services.StockService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by yangyu on 3/10/17.
 */
@Service
public class StockServiceImpl implements StockService {
    private DataSource mDataSource;
    private Connection mConnection;

    public StockServiceImpl() {
        mDataSource = DataSourcesUtility.userDataSource;
        try {
            mConnection = mDataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public List<UserStock> getProfileStocks(UserProfile profile) {
        return null;
    }

    @Override
    public boolean createNewStock(UserProfile profile, String symbol, float price, float share, Date boughtDate) {
        return false;
    }

    @Override
    public boolean deleteOneStock(UserStock stock) {
        return false;
    }

    @Override
    public boolean updateStockPrice(UserStock stock, float price) {
        return false;
    }

    @Override
    public boolean updateStockShare(UserStock stock, float share) {
        return false;
    }

    @Override
    public boolean updateStockBoughtDate(UserStock stock, Date newDate) {
        return false;
    }
}
