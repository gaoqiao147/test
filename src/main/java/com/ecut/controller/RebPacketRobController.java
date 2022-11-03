package com.ecut.controller;

import com.ecut.model.RedPacketRob;
import com.ecut.model.RedPacketSend;
import com.ecut.service.RedPacketRobService;
import com.ecut.service.RedPacketSendService;
import com.ecut.util.CommonResult;
import com.ecut.vo.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author zhouwei
 */
@RestController
@RequestMapping("/rob-red-packet")
public class RebPacketRobController {
    @Resource
    RedPacketRobService redPacketRobService;

    @PostMapping
    public ResultVo robRedPacket(@RequestBody RedPacketRob redPacketRob) {
        HashMap<String, Object> map = redPacketRobService.robRedPacket(redPacketRob);
        if (map.isEmpty()) {
            return CommonResult.fail();
        } else {
            return CommonResult.success(map);
        }
    }
}
