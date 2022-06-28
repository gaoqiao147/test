package com.ecut.controller;


import com.ecut.model.SubjectScoreDO;
import com.ecut.service.SubjectScoreService;
import com.ecut.util.CommonResult;
import com.ecut.vo.PageVo;
import com.ecut.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/subjectScoreDO")
public class SubjectScoreController {
    @Autowired
    private SubjectScoreService subjectScoreService;

    @PostMapping("/allScore")
    public ResultVo selectAllScore(){
        List<SubjectScoreDO> allStudentList = new ArrayList<>();
        allStudentList = subjectScoreService.selectAllStudentScore();
        if(allStudentList == null ){
            return CommonResult.fail();
        }else{
            return CommonResult.success(allStudentList);
        }
    }

    @GetMapping ("/ScoreByDate/{stuDate}")
    public ResultVo selectScoreByDate(@PathVariable("stuDate") String date){
        List<SubjectScoreDO> studentListScoreByDate = subjectScoreService.scoreByDate(date);
        if(studentListScoreByDate == null ){
            return CommonResult.fail();
        }else{
            return CommonResult.success(studentListScoreByDate);
        }
    }
    @PostMapping ("/ScoreByPage")
    public ResultVo allScorePage(@RequestBody PageVo pageVo){
        return subjectScoreService.studentScorePage(pageVo);
    }

    @GetMapping("/scoreFuzzyQuery/{subject}")
    public ResultVo scoreFuzzyQuery(@PathVariable("subject") String subject){
        List<SubjectScoreDO> StudentListFuzzyQuery = subjectScoreService.scoreFuzzyQuery(subject);
        if(StudentListFuzzyQuery == null ){
            return CommonResult.fail();
        }else{
            return CommonResult.success(StudentListFuzzyQuery);
        }
    }
    @PostMapping("/avgSubjectScore")
    public ResultVo avgSubjectScore(){
        Map<String,Double> map = subjectScoreService.avgSubjectScore();
        if(map == null){
            return CommonResult.fail();
        }else{
            return CommonResult.success(map);
        }
    }
}

