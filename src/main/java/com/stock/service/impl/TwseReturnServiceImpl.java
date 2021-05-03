package com.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.mapper.StockMapper;
import com.stock.model.Stock;
import com.stock.model.StockEnum;
import com.stock.parse.Parse_1;
import com.stock.read.Get;
import com.stock.service.TwseReturnService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description
 * @Param $
 * @return $
 **/
public class TwseReturnServiceImpl implements TwseReturnService {

    @Resource
    StockMapper stockMapper;

    @Override
    public void twseReturnMonth(String yyyyMM01) throws Exception {
        String url = "https://www.twse.com.tw/indicesReport/MFI94U?response=html&date=";
        url = url + yyyyMM01;
        //check repeat
        QueryWrapper<Stock> wrapper = new QueryWrapper<>();
        wrapper.likeRight("stockId", yyyyMM01.substring(0, 6));
        wrapper.likeLeft("stockId", StockEnum.taiex_total_return.getStockCode());
        List<Stock> stocks_old = stockMapper.selectList(wrapper);

        Set<String> stockId_old = stocks_old.stream().map(Stock -> Stock.getSId()).collect(Collectors.toSet());

        String source = Get.doGet(url);
        ArrayList<Stock> stocks = Parse_1.parseData_1(source);
        for (Stock stock : stocks) {
            if (!stockId_old.contains(stock.getSId())) {
                stockMapper.insert(stock);
            }


        }
    }
}
