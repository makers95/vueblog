package com.markerhub.stock.save;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.mapper.StockMapper;
import com.stock.model.Stock;
import com.stock.model.StockEnum;
import com.stock.parse.Parse_1;
import com.stock.read.Get;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description 抓取台灣加權指數含息資料
 * @Param $
 * @return $
 **/

@SpringBootTest
public class TwseReturn {

//    @Autowired
//    private StockMapper stockMapper;

    @Autowired
    private StockMapper stockMapper;

    @Test
    public void Test() throws Exception {
        System.out.println("123");
//        Get Get = new Get();
//        String url = "https://www.twse.com.tw/indicesReport/MFI94U?response=html&date=20210301";
//        String source = Get.doGet(url);
//        System.out.println(source);

//        List<String> parameters = get_1.getParmaList();
//        parameters.forEach(System.out::println);
    }

    //股市總加權指數
    @Test
    public void crawler() throws Exception {

        String url = "https://www.twse.com.tw/indicesReport/MFI94U?response=html&date=";
        //產生參數陣列
        List<String> parameters = Get.getParmaList(2003);
        //check repeat
        QueryWrapper<Stock> wrapper = new QueryWrapper<>();
        wrapper.likeLeft("stockId", StockEnum.taiex_total_return.getStockCode());
        List<Stock> stocks_old = stockMapper.selectList(wrapper);

        Set<String> stockId_old = stocks_old.stream().map(Stock -> Stock.getSId()).collect(Collectors.toSet());

        for (String param : parameters) {
            ArrayList<Stock> Stocks = new ArrayList<>();
            String source = "";
            System.out.println(url + param);
            source = Get.doGet(url + param);
            Thread.sleep(3500);
            Stocks = Parse_1.parseData_1(source);
            for (Stock stock : Stocks) {
                if(!stockId_old.contains(stock.getSId())){
                    stockMapper.insert(stock);
                }

            }
        }
    }
}





