package com.ecut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ecut.model.LoginDO;
import com.ecut.mapper.LoginMapper;
import com.ecut.service.LoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecut.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhouwei
 * @since 2022-06-25
 */
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, LoginDO> implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public ResultVo loginVerification(LoginDO loginDO) {
        //前端传递的值在是实现类验证
        QueryWrapper<LoginDO> loginQueryWrapper = new QueryWrapper<>();
        loginQueryWrapper.eq("usernumber",loginDO.getUsernumber());
        LoginDO loginVerify = this.loginMapper.selectOne(loginQueryWrapper);
        //定义一个公共返回类
        ResultVo resultVo = new ResultVo<LoginDO>();
        //loginVerify为空表示数据空中无数据, -1代表无数据
        if(loginVerify == null){
            resultVo.setCode(-1);
        }else{
            //判断传入的密码是否匹配 , -2代表密码错误
            if(!loginVerify.getPassword().equals(loginDO.getPassword())){
                resultVo.setCode(-2);
            }else{
                resultVo.setCode(1);
                resultVo.setData(loginVerify);
            }
        }
        return resultVo;
    }
}
