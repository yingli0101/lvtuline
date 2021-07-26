package com.lvtuline.order.controller;

import com.lvtuline.common.feign.StockFeignClients;
import com.lvtuline.order.produce.OrderMsgProduce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * server.port=17888
 * spring.application.name=orderServer
 * spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848
 * spring.cloud.nacos.config.server-addr=127.0.0.1:8848
 * spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
 * spring.datasource.url=jdbc:mysql://locahost:3306/lvtuline?useUnicode=true&characterEncoding=utf8useSSL=false
 * spring.datasource.username=root
 * spring.datasource.password=root
 */
@Api(value = "Order控制层接口api")
@RestController
public class OrderController {

    @Resource
    StockFeignClients stockFeignClients;

    @Resource
    OrderMsgProduce orderMsgProduce;


    @ApiOperation(value = "下单接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", dataType = "string", required = true),
            @ApiImplicitParam(name = "requestId", dataType = "string", required = true)
    })
    @PostMapping("createOrder")
    //@GlobalTransactional
    public void createOrder() {
        //TODO 可调用三方支付创建订单，订单创建成功，根据商品去查询库存，如果库存足够则减库存
        // 如果库存不够 回滚下单业务
        System.out.println("下单");
        orderMsgProduce.sendMsg("下单成功,发给库存系统减库存！");
        //stockFeignClients.delStock(1);
    }


}
