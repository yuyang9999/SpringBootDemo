package hello.services;

import hello.models.dbmodel.StockHistory;

import java.util.Date;
import java.util.List;

/**
 * Created by yangyu on 4/10/17.
 */
public interface StockHistoryService {
    StockHistory createStockHistory(String symbolName, Date date,float open, float high, float low, float close, float adjClose, int volume);
    StockHistory getStockHisotryWithSymbolNameAndDate(String symbol, Date date);
    List<StockHistory> getStockHistoriesWithSymbolNameAndDateRange(String symbol, Date date1, Date date2);
}
