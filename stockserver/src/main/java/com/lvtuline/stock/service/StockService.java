package com.lvtuline.stock.service;

import com.lvtuline.common.ResultCode;
import com.lvtuline.common.ResultVo;
import com.lvtuline.stock.mapper.StockMapper;
import com.lvtuline.stock.pojo.Stock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StockService {

    @Resource
    StockMapper stockMapper;

    public ResultVo delStock(long id) {
        int i = stockMapper.updateById(id);
        if (i > 0) {
            return new ResultVo(ResultCode.SUCCESS);
        }
        return new ResultVo(ResultCode.FAIL);
    }


}
