package com.lvtuline.stock.pojo;

import lombok.Data;

@Data
public class Stock {
    private long id;
    private String name;
    private String price;
    private Integer number;
}
