package com.stock.parse;


import com.stock.model.Stock;
import com.stock.model.StockEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.util.ArrayList;
import java.util.Locale;

public class Parse_1 {
    public Parse_1() {
    }

    public static ArrayList<Stock> parseData_1(String source) throws ParseException {

        Document doc = Jsoup.parse(source);
        Elements tds = doc.select("td");

        ArrayList<Stock> Stocks = new ArrayList<>();

        for (int i = 0; i < tds.size(); i = i + 2) {
            Stock stock = new Stock();
            String skip = "日　期";
            String skip_1 = "發行量加權股價報酬指數";

            if (tds.get(i).text().contains(skip) || tds.get(i + 1).text().contains(skip_1)) {
                continue;
            }

            //原始資料
            String column_date = tds.get(i).text(); //yyy/mm/dd
            String column_price = tds.get(i + 1).text();

            //處理日期
            Chronology chrono = MinguoChronology.INSTANCE;
            DateTimeFormatter df = new DateTimeFormatterBuilder().parseLenient()
                    .appendPattern("yyy/MM/dd").toFormatter().withChronology(chrono)
                    .withDecimalStyle(DecimalStyle.of(Locale.getDefault()));
            ChronoLocalDate taiwan_date = chrono.date(df.parse(column_date));
            LocalDate localDate = LocalDate.from(taiwan_date);

            Float price = Float.parseFloat(column_price.replace(",", ""));
            stock.setSId(localDate.toString().replace("-", "") + StockEnum.taiex_total_return.getStockCode());
            stock.setDate(localDate);
            stock.setName("TAIEX_TotalReturn");
            stock.setPrice(price);
            Stocks.add(stock);
        }
        return Stocks;
    }


}
