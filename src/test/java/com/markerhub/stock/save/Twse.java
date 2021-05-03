package com.markerhub.stock.save;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.mapper.StockDetailMapper;
import com.stock.model.StockDetail;
import com.stock.model.StockEnum;
import com.stock.parse.Parse_2;
import com.stock.read.Get;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
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
public class Twse {

    @Resource
    private StockDetailMapper stockDetailMapper;

    @Test
    public void Test() throws Exception {
        String timeParam = "20210401";
        String url = "https://www.twse.com.tw/indicesReport/MI_5MINS_HIST?response=html&date="+timeParam ;
        try {
        String source = Get.doGet(url);

        //check repeat
        QueryWrapper<StockDetail> wrapper = new QueryWrapper<>();
        wrapper.likeRight("stockId",timeParam.substring(0,6));
        wrapper.likeLeft("stockId", StockEnum.taiex.getStockCode());
        List<StockDetail> stocks_old = stockDetailMapper.selectList(wrapper);
        Set<String> stockId_old = stocks_old.stream().map(stock -> stock.getSId()).collect(Collectors.toSet());

                ArrayList<StockDetail> stocks = new ArrayList<>();
                stocks = Parse_2.parse(source);

                for (StockDetail stock : stocks) {
                    if(!stockId_old.contains(stock.getSId())){
                        System.out.println(stock);
                        stockDetailMapper.insert(stock);
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //股市總加權指數
    @Test
    public void crawler() throws Exception {

        String url = "https://www.twse.com.tw/indicesReport/MI_5MINS_HIST?response=html&date=";


        //產生參數陣列 from 1999
        List<String> parameters = Get.getParmaList(2010);

        //check repeat
        QueryWrapper<StockDetail> wrapper = new QueryWrapper<>();
        wrapper.likeLeft("stockId", StockEnum.taiex.getStockCode());
        List<StockDetail> stocks_old = stockDetailMapper.selectList(wrapper);
        Set<String> stockId_old = stocks_old.stream().map(stock -> stock.getSId()).collect(Collectors.toSet());
        try {
            for (String param : parameters) {
                ArrayList<StockDetail> stocks = new ArrayList<>();
                String source = "";
                System.out.println(url + param);
                source = Get.doGet(url + param);
                Thread.sleep(3500);
                stocks = Parse_2.parse(source);
                for (StockDetail stock : stocks) {
                    if(!stockId_old.contains(stock.getSId())){
                        System.out.println(stock);
                        stockDetailMapper.insert(stock);
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





