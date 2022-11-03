package com.ecut.model;

import lombok.Data;

import java.util.Date;

/**
 * @author zhouwei
 */
@Data
public class RedPacketRob {
    private String id;
    private String redId;
    private String userId;
    private Double robAmount;
    private Date robTime;
    private String sendUserId;
}
