package com.ecut.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhouwei
 * @since 2022-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("login")
public class LoginDO implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "usernumber", type = IdType.AUTO)
    private Integer usernumber;

    private String password;

    private String username;


}
