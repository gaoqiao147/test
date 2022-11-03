package com.ecut.controller;

import com.ecut.service.SubjectScoreService;
import com.ecut.util.CommonResult;
import com.ecut.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouwei
 */
@CrossOrigin
@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Autowired
    private SubjectScoreService subjectScoreService;

    @GetMapping("/stuScore")
    public ResultVo stuScore() {
        Map<String, Object> map = new HashMap<>();
        map = subjectScoreService.stuScore();
        return CommonResult.success(map);
    }

    @GetMapping("/stuScoreBySubject/{subject}")
    public ResultVo stuScoreBySubject(@PathVariable("subject") String subject) {
        Map<String, Object> map = new HashMap<>();
        map = subjectScoreService.stuScoreBySubject(subject);
        return CommonResult.success(map);
    }
}
