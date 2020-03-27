package com.steven.www.multidatasourcedemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 这个注解如果使用mybatis就要加的，不然扫描不到Mapper
@MapperScan("com.steven.www.multidatasourcedemo.mapper")
public class MultiDatasourceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiDatasourceDemoApplication.class, args);
    }

}
