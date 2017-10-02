package hello.models;

import lombok.Data;

import java.util.Date;

/**
 * Created by yangyu on 2/10/17.
 */
@Data
public class UserStock {
    private int stockId;

    private String symbolName;

    private float shares;

    private float price;

    Date boughtDate;
}
