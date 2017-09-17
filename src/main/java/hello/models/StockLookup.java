package hello.models;

import hello.apis.APIStocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


/**
 * Created by yangyu on 17/9/17.
 */
public class StockLookup {
    private static Map<String, List<StockInfo>> stockLookup;
    private static Set<String> stockNames;

    static {
        //load stock symbols
        stockLookup = new HashMap<String, List<StockInfo>>();
        stockNames = new HashSet<String>();

        InputStream stream = APIStocks.class.getResourceAsStream("/companylist.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        String line;
        int cnt = 0;
        try {
            while ((line = reader.readLine()) != null) {
                cnt += 1;
                if (cnt == 1) {
                    //skip the first line
                    continue;
                }
                String[] parts = line.split("\",\"");
                String symbol = parts[0].substring(1).trim();

                stockNames.add(symbol);

                String companyName = parts[1].trim();
                StockInfo info = new StockInfo(symbol, companyName);

                //use the first character to do lookup
                String firstCh = symbol.substring(0, 1);
                if (!stockLookup.containsKey(firstCh)) {
                    stockLookup.put(firstCh, new ArrayList<StockInfo>());
                }

                List<StockInfo> infos = stockLookup.get(firstCh);
                infos.add(info);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static List<StockInfo> getStocks(String startSymbol) {
        List<StockInfo> ret = new ArrayList<StockInfo>();

        //get the first char
        String firstCh = startSymbol.substring(0, 1);
        if (stockLookup.containsKey(firstCh)) {
            List<StockInfo> infos = stockLookup.get(firstCh);
            for (StockInfo info: infos) {
                String name = info.getSymbol();
                if (name.startsWith(startSymbol)) {
                    ret.add(info);
                }
            }
        }

        return ret;
    }



}
