package com.lvtuline.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVo implements Serializable {

    private Integer code;
    private String message;
    private Object data;

    public ResultVo(ResultCode resultCode, Object data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    public ResultVo(ResultCode resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public ResultVo() {
    }
}
