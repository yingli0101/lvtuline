package com.lvtuline.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface StockMapper extends BaseMapper {

    @Update("update stock set number = number-1 where id=#{id}")
    int updateById(long id);
}
