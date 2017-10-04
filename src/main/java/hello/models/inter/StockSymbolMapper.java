package hello.models.inter;

import hello.models.dbmodel.StockSymbol;

public interface StockSymbolMapper {
    int deleteByPrimaryKey(Integer stockId);

    int insert(StockSymbol record);

    int insertSelective(StockSymbol record);

    StockSymbol selectByPrimaryKey(Integer stockId);

    int updateByPrimaryKeySelective(StockSymbol record);

    int updateByPrimaryKey(StockSymbol record);

    StockSymbol selectBySymbolName(String symbolName);
}