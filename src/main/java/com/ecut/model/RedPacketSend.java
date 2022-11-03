package com.ecut.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhouwei
 */
@Data
public class RedPacketSend {
    private String id;

    @NotBlank(message = "用户id不能为空！")
    private String userId;

    @Min(value = 0,message = "不能小于0元!")
    @NotNull(message = "红包金额不能为空！")
    private Double money;

    @Min(value = 0,message = "不能小于0个红包!")
    @NotNull(message = "红包数量不能为空！")
    private Integer number;
    private String isFlag;
}
