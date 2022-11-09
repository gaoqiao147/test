package com.ecut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ecut.model.LoginDO;
import com.ecut.mapper.LoginMapper;
import com.ecut.rabbitmq.publisher.LogPublisher;
import com.ecut.service.LoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecut.util.RedisUtil;
import com.ecut.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
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
    @Resource
    private LoginMapper loginMapper;

    @Resource
    LogPublisher logPublisher;

    @Override
    public ResultVo loginVerification(LoginDO loginDO) {
        //定义一个公共返回类
        ResultVo resultVo = new ResultVo<LoginDO>();
        //将前端传的值先从redis中查找，没有值就在redis中创建
        String host = "1.117.87.146";
        int port = 6379;
        Jedis jedis = null;
        JedisPool pool = null;
        try {
            pool = RedisUtil.open(host,port);
            jedis = pool.getResource();
            if(jedis.get(loginDO.getUsernumber().toString()) != null){
                if(jedis.get(loginDO.getUsernumber().toString()).equals(loginDO.getPassword())){
                    resultVo.setCode(1);
                    resultVo.setData(jedis.get(loginDO.getUsernumber().toString()));
                    //发送队列消息
                    logPublisher.sendLogMsg(loginDO);
                    System.out.println("使用了redis登录成功");
                }else {
                    resultVo.setCode(-2);
                    System.out.println("使用了redis登录失败");
                }
            }else{
                //前端传递的值在是实现类验证
                QueryWrapper<LoginDO> loginQueryWrapper = new QueryWrapper<>();
                loginQueryWrapper.eq("usernumber",loginDO.getUsernumber());
                LoginDO loginVerify = this.loginMapper.selectOne(loginQueryWrapper);
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
                        //发送队列消息
                        logPublisher.sendLogMsg(loginDO);
                    }
                }
                //在redis创建学生信息
                jedis.set(loginDO.getUsernumber().toString(),loginDO.getPassword());
            }
        }catch (Exception e){
            e.fillInStackTrace();
        }
        return resultVo;
    }
}
