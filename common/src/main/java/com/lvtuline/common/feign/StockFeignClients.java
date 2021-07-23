package com.lvtuline.common.feign;

import com.lvtuline.common.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "stockserver")
public interface StockFeignClients {


    @PostMapping("delStock/{id}")
    @ResponseBody
    ResultVo delStock(@PathVariable long id);
}
