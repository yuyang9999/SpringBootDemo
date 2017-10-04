package hello.services.impl;

import hello.models.dbmodel.StockSymbol;
import hello.models.inter.StockSymbolMapper;
import hello.services.StockSymbolService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by yangyu on 4/10/17.
 */
@Service
public class StockSymbolServiceImpl implements StockSymbolService {
    private StockSymbolMapper mapper;

    public StockSymbolServiceImpl() {
        mapper = BatisMappers.stockSymbolMapper;
    }

    @Override
    public StockSymbol addSymbol(String symbolName, String name, String sector, String industry) {
        if (StringUtils.isBlank(symbolName) || StringUtils.isBlank(name)) {
            return null;
        }

        StockSymbol symbol = new StockSymbol();
        symbol.setSymbol(symbolName);
        symbol.setName(name);
        symbol.setSector(sector);
        symbol.setIndustry(industry);

        int cnt = mapper.insertSelective(symbol);

        if (cnt == 1) {
            return symbol;
        } else {
            return null;
        }
    }

    @Override
    public boolean isSymbolExists(String symName) {
        StockSymbol symbol = mapper.selectBySymbolName(symName);

        return symbol != null;
    }

    @Override
    public StockSymbol getSymbolBySName(String symbolName) {
        StockSymbol symbol = mapper.selectBySymbolName(symbolName);

        return symbol;
    }
}
