package hello.apis;

import hello.models.StockInfo;
import hello.models.StockLookup;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangyu on 17/9/17.
 */
@RestController
public class APIStocks {
    private static final String apiVersion = ApiConfig.apiVersion;

    @RequestMapping(apiVersion + "queryStocks")
    List<StockInfo> getStockSymbols(@RequestParam(value = "symbol") String symbol) {
        List<StockInfo> ret = StockLookup.getStocks(symbol);

        return ret;
    }

}
