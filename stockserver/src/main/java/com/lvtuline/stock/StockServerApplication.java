package com.lvtuline.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.lvtuline.stock.mapper")
public class StockServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockServerApplication.class);
    }
}
