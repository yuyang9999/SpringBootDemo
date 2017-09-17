package hello.models;

import lombok.Data;
import lombok.Getter;

/**
 * Created by yangyu on 17/9/17.
 */
@Data
public class StockInfo {
    private String symbol;
    private String companyName;

    public StockInfo(String symbol, String companyName) {
        this.symbol = symbol;
        this.companyName = companyName;
    }
}
