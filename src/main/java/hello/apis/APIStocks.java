package hello.apis;

import hello.models.StockInfo;
import hello.models.StockLookup;
import hello.models.dbmodel.StockHistory;
import hello.services.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yangyu on 17/9/17.
 */
@RestController
public class APIStocks {
    private static final String apiVersion = ApiConfig.apiVersion;

    @Autowired
    private StockHistoryService historyService;

    @RequestMapping(apiVersion + "queryStocks")
    List<StockInfo> getStockSymbols(@RequestParam(value = "symbol") String symbol) {
        List<StockInfo> ret = StockLookup.getStocks(symbol);

        return ret;
    }

    @RequestMapping(apiVersion + "stockHistory")
    ApiResponse getStockHistory(@RequestParam(value = "symbol") String symbol) {
        Date d1 = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);
        Date d2 = cal.getTime();
        List<StockHistory> query = historyService.getStockHistoriesWithSymbolNameAndDateRange(symbol, d2, d1);
        if (query == null || query.size() == 0) {
            return new ApiResponse("could not get history date");
        }

        return new ApiResponse(false, "", query);
    }

}
