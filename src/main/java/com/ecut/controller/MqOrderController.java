package com.ecut.controller;

import com.ecut.model.MqOrderAll;
import com.ecut.service.MqOrderAllService;
import com.ecut.util.CommonResult;
import com.ecut.vo.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhouwei
 */
@RestController
@RequestMapping("/order")
public class MqOrderController {
    @Resource
    MqOrderAllService mqOrderAllService;

    @PostMapping
    public ResultVo buy(@RequestBody MqOrderAll mqOrderAll){
        int res = mqOrderAllService.saveAllOrder(mqOrderAll);
        if(res == 0){
            return CommonResult.fail();
        }
        return CommonResult.success(mqOrderAll);
    }
}
