package com.ecut.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author zhouwei
 */
@Accessors(chain = true)
@Data
public class RedPacketRecord {
    private String id;
    private String redId;
    private Double amount;
    private Date createTime;
    private String isFlag;
}
