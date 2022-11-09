package com.ecut.model;

import lombok.Data;

import java.util.Date;

/**
 * @author zhouwei
 */
@Data
public class SysLog {
    private String id;
    private String userId;
    private String module;
    private String data;
    private String memo;
    private Date createTime;
}