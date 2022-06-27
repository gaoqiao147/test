package com.ecut.vo;

import lombok.Data;

/**
 * @author zhouwei
 */
@Data
public class ResultVo<T> {
    private Integer code;
    private T data;
}
