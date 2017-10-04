package hello.services.impl;

import hello.models.dbmodel.StockHistory;
import hello.models.inter.StockHistoryMapper;
import hello.services.StockHistoryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangyu on 4/10/17.
 */
@Service
public class StockHistoryServiceImpl implements StockHistoryService {
    private StockHistoryMapper mapper;

    public StockHistoryServiceImpl() {
        mapper = BatisMappers.stockHistoryMapper;
    }

    @Override
    public StockHistory createStockHistory(String symbolName, Date date, float open, float high, float low, float close, float adjClose, int volume) {
        StockHistory history = new StockHistory();
        history.setSymbol(symbolName);
        history.setDate(date);
        history.setOpen(open);
        history.setHigh(high);
        history.setLow(low);
        history.setClos(close);
        history.setAdjClose(adjClose);
        history.setVolume(volume);

        int cnt = mapper.insertSelective(history);

        if (cnt == 1) {
            return history;
        } else {
            return null;
        }
    }

    @Override
    public StockHistory getStockHisotryWithSymbolNameAndDate(String symbol, Date date) {
        return mapper.selectBySymbolNameAndDate(symbol, date);
    }

    @Override
    public List<StockHistory> getStockHistoriesWithSymbolNameAndDateRange(String symbol, Date date1, Date date2) {
        return mapper.selectBySymbolNameAndDateRange(symbol, date1, date2);
    }
}
