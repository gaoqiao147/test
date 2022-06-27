package com.ecut.util;

import com.ecut.vo.ResultVo;

/**
 * 配置公共返回方法
 *
 * @author zhouwei
 */
public class CommonResult {
    public static ResultVo success(Object object){
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(1);
        resultVo.setData(object);
        return resultVo;
    }

    public static ResultVo fail(){
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(-1);
        return resultVo;
    }
}
