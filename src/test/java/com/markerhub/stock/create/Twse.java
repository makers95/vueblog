package com.markerhub.stock.create;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.entity.Blog;
import com.markerhub.mapper.BlogMapper;
import com.stock.mapper.StockDetailMapper;
import com.stock.mapper.StockMapper;
import com.stock.model.Stock;
import com.stock.model.StockDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 將資料存入db
 * @Param $
 * @return $
 **/

@SpringBootTest
public class Twse {

    @Resource
    private StockDetailMapper stockDetailMapper;
    @Resource
    private StockMapper stockMapper;
    @Resource
    private BlogMapper blogMapper;

    @Test
    public void create() throws Exception {


        for (int k = 2013; k <=2021; k++) {
            String year = ""+k;
            for (int j = 1; j <= 12; j++) {
                String month = ""+j;
                if(j<10){
                    month = 0+month;
                }
                String dateCondition = year+"-"+month;
                QueryWrapper<Stock> stockQueryWrapper = new QueryWrapper<>();
                stockQueryWrapper.likeRight("date",dateCondition);
                List<Stock> stocks = stockMapper.selectList(stockQueryWrapper);
                System.out.println(stocks);
                QueryWrapper<StockDetail> stockDetailQueryWrapper = new QueryWrapper<>();
                stockDetailQueryWrapper.likeRight("date", dateCondition);
                List<StockDetail> stockDetails = stockDetailMapper.selectList(stockDetailQueryWrapper);
                System.out.println(stockDetails);

                Blog blog = new Blog();
                //set data
                StringBuilder content = new StringBuilder();
                String br = "\n";
                blog.setUserId(1l);
                blog.setStatus(0);
                blog.setTitle(k+"年"+j+"月台灣股市歷史資料");
                blog.setDescription("發行量加權股價指數和報酬指數");
                blog.setCreated(LocalDateTime.now());
                blog.setStatus(0);
                //default
                content.append("|日期|開盤指數|最高指數|最低指數|收盤指數|累計報酬指數|" + br);
                content.append("|:-:|:-:|:-:|:-:|:-:|:-:|" + br);
                if(stocks.size() !=stockDetails.size()){
                    throw new Exception("陣列資料有誤");
                }
                for (int i = 0; i < stocks.size(); i++){
                    String interval = "|";
                    com.stock.dto.Twse twse = new com.stock.dto.Twse();
                    BeanUtils.copyProperties(stocks.get(i), twse);
                    BeanUtils.copyProperties(stockDetails.get(i), twse);
                    content.append(interval+twse.getDate());
                    content.append(interval+twse.getPriceOpen());
                    content.append(interval+twse.getPriceHigh());
                    content.append(interval+twse.getPriceLow());
                    content.append(interval+twse.getPriceEnd());
                    content.append(interval+twse.getPrice());
                    content.append(interval+br);
                }
                blog.setContent(content.toString());
                blogMapper.insert(blog);
                if(k==2021&& j==4){
                    break;
                }
            }

        }






    }

    private String toColumn(com.stock.dto.Twse twse) {
        String between = "|";
        String response = between + twse.getDate()
                        + between + twse.getPriceOpen()
                        + between + twse.getPriceHigh()
                        + between + twse.getPriceLow()
                        + between + twse.getPriceEnd()
                        + between + twse.getPrice()
                        + between + "\n";
        return response;
    }
}





