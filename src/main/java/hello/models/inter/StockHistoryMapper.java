package hello.models.inter;

import hello.models.dbmodel.StockHistory;
import hello.services.StockHistoryService;

import java.util.Date;
import java.util.List;

public interface StockHistoryMapper {
    int deleteByPrimaryKey(Integer hId);

    int insert(StockHistory record);

    int insertSelective(StockHistory record);

    StockHistory selectByPrimaryKey(Integer hId);

    int updateByPrimaryKeySelective(StockHistory record);

    int updateByPrimaryKey(StockHistory record);

    List<StockHistory> selectBySymbolNameAndDateRange(String symbolName, Date date1, Date date2);

    StockHistory selectBySymbolNameAndDate(String symbolName, Date date);
}