package com.lvtuline.stock.controller;

import com.lvtuline.common.ResultVo;
import com.lvtuline.stock.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("库存接口")
@RestController
public class StockController {

    @Resource
    StockService stockService;


    @ApiOperation(value = "减库存接口")
    @PostMapping("delStock/{id}")
    public ResultVo delStock(@PathVariable long id) {
        //TODO 减库存
        System.out.println("减少库存");
        return stockService.delStock(id);

    }

}
