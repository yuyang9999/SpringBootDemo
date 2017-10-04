package hello.services;

import hello.models.dbmodel.StockSymbol;

/**
 * Created by yangyu on 4/10/17.
 */
public interface StockSymbolService {
    StockSymbol addSymbol(String symbolName, String name, String sector, String industry);
    boolean isSymbolExists(String symbol);
    StockSymbol getSymbolBySName(String symbolName);
}
