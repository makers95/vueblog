package com.stock.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDetail {
    @TableId(value = "stockId",type = IdType.ASSIGN_ID)
    private String sId;

    private String name;
    private Float priceHigh;
    private Float priceLow;
    private Float priceOpen;
    private Float priceEnd;
    private LocalDate date;
}
