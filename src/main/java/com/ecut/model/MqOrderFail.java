package com.ecut.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author zhouwei
 */
@Data
@Accessors(chain = true)
public class MqOrderFail {
    private String id;
    private String orderId;
    private String orderName;
    private Date creatTime;
}
