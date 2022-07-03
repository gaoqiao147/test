//package com.ecut.test;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.ecut.mapper.SubjectScoreMapper;
//import com.ecut.model.SubjectScoreDO;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//public class EchartsTest {
//
//    @Autowired
//    private SubjectScoreMapper subjectScoreMapper;
//    @Test
//    public void test(){
//        String name = "科技英语阅读";
//        QueryWrapper<SubjectScoreDO> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("subject",name);
//
//        System.out.println(this.subjectScoreMapper.selectList(queryWrapper));
//    }
//}
