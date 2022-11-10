package com.ecut.mapper;

import com.ecut.model.LoginDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhouwei
 * @since 2022-06-25
 */
public interface LoginMapper extends BaseMapper<LoginDO> {
    /**
     * 注册
     *
     * @param login
     * @return
     */
    int registered(@Param("login") LoginDO login);

    /**
     * 查询用户
     *
     * @param usernumber
     * @return
     */
    LoginDO info(@Param("usernumber") Integer usernumber);
}
