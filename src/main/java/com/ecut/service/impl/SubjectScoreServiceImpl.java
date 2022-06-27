package com.ecut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecut.model.SubjectScoreDO;
import com.ecut.mapper.SubjectScoreMapper;
import com.ecut.service.SubjectScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecut.util.CommonResult;
import com.ecut.vo.PageVo;
import com.ecut.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhouwei
 * @since 2022-06-25
 */
@Service
public class SubjectScoreServiceImpl extends ServiceImpl<SubjectScoreMapper, SubjectScoreDO> implements SubjectScoreService {
    @Autowired
    private SubjectScoreMapper subjectScoreMapper;

    @Override
    public List<SubjectScoreDO> selectAllStudentScore() {
        List<SubjectScoreDO> allStudentScoreList = new ArrayList<>();
        allStudentScoreList = subjectScoreMapper.selectList(null);
        return allStudentScoreList;
    }

    /**
     * 根据学期查询学生成绩
     * @param date 学期
     * @return
     */
    @Override
    public List<SubjectScoreDO> scoreByDate(String date) {
        QueryWrapper<SubjectScoreDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date",date);
        List<SubjectScoreDO> StudentScoreListByDate = new ArrayList<>();
        StudentScoreListByDate = this.subjectScoreMapper.selectList(queryWrapper);
        return StudentScoreListByDate;
    }

    /**
     * 学生成绩的分页实现
     * @param pageVo 分页
     * @return
     */
    @Override
    public ResultVo studentScorePage(PageVo pageVo) {
        //1.将pageVo类赋值给page
        Page<SubjectScoreDO> page = new Page<>(pageVo.getCurrentPage(), pageVo.getPageSize());
        //2.按学号排序
        LambdaQueryWrapper<SubjectScoreDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SubjectScoreDO::getUsernumber);
        //3.将条件给mybatisPlus中的分页方法
        Page<SubjectScoreDO> scorePage = subjectScoreMapper.selectPage(page,queryWrapper);
        //4.将查询出的结果赋值给list数组
        List<SubjectScoreDO> scoreListPage = scorePage.getRecords();

        if(scoreListPage == null){
            return CommonResult.fail();
        }else{
            return CommonResult.success(scoreListPage);
        }
    }

    @Override
    public List<SubjectScoreDO> scoreFuzzyQuery(String subject) {
        //1.定义queryWrapper
        QueryWrapper<SubjectScoreDO> queryWrapper = new QueryWrapper<>();
        //当Str为空白或者null时，isNotBlank返回false
        //当Str的length>0时，isNotBlank返回true
        queryWrapper.like(StringUtils.isNotBlank(subject),"subject",subject);
        List<SubjectScoreDO> scoreFuzzyQueryList = subjectScoreMapper.selectList(queryWrapper);
        return scoreFuzzyQueryList;
    }
}
