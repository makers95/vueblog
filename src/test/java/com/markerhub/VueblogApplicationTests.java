package com.markerhub;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.markerhub.stock.mapper")
class VueblogApplicationTests {

    @Test
    void contextLoads() {
    }

}
