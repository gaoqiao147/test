package com.ecut.controller;


import com.ecut.model.LoginDO;
import com.ecut.service.LoginService;
import com.ecut.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhouwei
 * @since 2022-06-25
 */
@CrossOrigin
@RestController
@RequestMapping("/loginDO")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/loginVerify")
    public ResultVo loginVerify(@RequestBody LoginDO loginDO){
        ResultVo<Object> resultVo  = loginService.loginVerification(loginDO);
        return resultVo;
    }
}

