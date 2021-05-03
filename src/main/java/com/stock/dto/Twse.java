package com.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @Description 合併股市資料 日資料和報酬資料
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Twse {
    private String sId;
    private String name;
    private Float price;
    private Float priceOpen;
    private Float priceHigh;
    private Float priceLow;
    private Float priceEnd;
    private LocalDate date;
}
