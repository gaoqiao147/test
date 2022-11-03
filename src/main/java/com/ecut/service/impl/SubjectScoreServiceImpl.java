package com.ecut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
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
import org.yaml.snakeyaml.events.Event;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhouwei
 * @since 2022-06-25
 */
@Service
public class SubjectScoreServiceImpl extends ServiceImpl<SubjectScoreMapper, SubjectScoreDO> implements SubjectScoreService {
    @Resource
    private SubjectScoreMapper subjectScoreMapper;

    @Override
    public List<SubjectScoreDO> selectAllStudentScore() {
        List<SubjectScoreDO> allStudentScoreList = new ArrayList<>();
        allStudentScoreList = subjectScoreMapper.selectList(null);
        return allStudentScoreList;
    }

    /**
     * 根据学期查询学生成绩
     *
     * @param date 学期
     * @return
     */
    @Override
    public List<SubjectScoreDO> scoreByDate(String date) {
        QueryWrapper<SubjectScoreDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date", date);
        List<SubjectScoreDO> StudentScoreListByDate = new ArrayList<>();
        StudentScoreListByDate = this.subjectScoreMapper.selectList(queryWrapper);
        return StudentScoreListByDate;
    }

    /**
     * 学生成绩的分页实现
     *
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
        Page<SubjectScoreDO> scorePage = subjectScoreMapper.selectPage(page, queryWrapper);
        //4.将查询出的结果赋值给list数组
        List<SubjectScoreDO> scoreListPage = scorePage.getRecords();
        if (scoreListPage == null) {
            return CommonResult.fail();
        } else {
            return CommonResult.success(scoreListPage);
        }
    }

    @Override
    public List<SubjectScoreDO> scoreFuzzyQuery(String subject) {
        //1.定义queryWrapper
        QueryWrapper<SubjectScoreDO> queryWrapper = new QueryWrapper<>();
        //当Str为空白或者null时，isNotBlank返回false
        //当Str的length>0时，isNotBlank返回true
        queryWrapper.like(StringUtils.isNotBlank(subject), "subject", subject);
        List<SubjectScoreDO> scoreFuzzyQueryList = subjectScoreMapper.selectList(queryWrapper);
        return scoreFuzzyQueryList;
    }

    @Override
    public Map<String, Double> avgSubjectScore() {
        //定义总成绩，平均分，科目数量,科目名字
        Integer totalScore1;
        Integer totalScore2 = 0;
        Double avgScore;
        Double subjectNum = 1.0;
        String subjectName1;
        String subjectName2 = null;
        Double id = 0.0;
        Map<String, String> subjectScore = new HashMap<>();
        Map<Integer, String> subjectScore1 = new HashMap<>();
        Map<String, Double> subjectScore2 = new HashMap<>();
        Map<String, Double> subjectScore3 = new HashMap<>();
        Map<String, Integer> subjectScore4 = new HashMap<>();
        /**
         * 1.查询出所有的科目，把科目相同的总成绩先算出来
         * 2.总成绩除以总学学生数量
         * 3.将科目和平均分分别放到map集合中
         */
        List<SubjectScoreDO> allSubjectList = subjectScoreMapper.selectList(null);
        System.out.println(allSubjectList);
        for (int i = 0; i < allSubjectList.size(); i++) {
            subjectName1 = allSubjectList.get(i).getSubject();
            totalScore1 = allSubjectList.get(i).getScore();
            subjectScore1.put(i, subjectName1);
            subjectScore.put(subjectName1, subjectName1);
            System.out.println(subjectScore1);
            System.out.println(subjectScore.size());
            for (int j = subjectScore.size(); j > 0; j--) {
                int flag = subjectScore.size();
                flag--;
                System.out.println("j= " + flag);
                if ((!Objects.equals(subjectName1, subjectScore1.get(j))) && (flag == 1)) {
                    subjectNum = 1.0;
                    totalScore2 = totalScore1;
                    subjectName2 = subjectName1;
                    System.out.println(subjectScore2);
                    avgScore = totalScore2 / subjectNum;
                    subjectScore2.put(subjectName2, subjectNum);
                    subjectScore3.put(subjectName2, avgScore);
                    subjectScore4.put(subjectName2, totalScore2);
                    break;
                }
                if (Objects.equals(subjectName1, subjectScore1.get(j + 1))) {
                    subjectNum = subjectScore2.get(subjectName1);
                    subjectNum = subjectNum + 1;
                    totalScore2 = subjectScore4.get(subjectName1);
                    totalScore2 = totalScore1 + totalScore2;
                    System.out.println(subjectScore2);
                    avgScore = totalScore2 / subjectNum;
                    subjectScore2.put(subjectName2, subjectNum);
                    subjectScore3.put(subjectName2, avgScore);
                    break;
                }
            }
        }
        return subjectScore3;
    }

    @Override
    public Map<String, Object> stuScore() {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<SubjectScoreDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject", "科技英语阅读");
        List<SubjectScoreDO> list = (List<SubjectScoreDO>) subjectScoreMapper.selectList(queryWrapper);
        List listName = new ArrayList<>();
        List listScore = new ArrayList<>();
        if (list != null) {
            //获取学生姓名
            for (SubjectScoreDO name : list) {
                listName.add(name.getUsername());
            }
            map.put("x", listName);
            //获取学生成绩
            for (SubjectScoreDO score : list) {
                listScore.add(score.getScore());
            }
            map.put("y", listScore);
        } else {
            map.put("x", "无数据");
        }
        return map;
    }

    @Override
    public Map<String, Object> stuScoreBySubject(String subject) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<SubjectScoreDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject", subject);
        List<SubjectScoreDO> list = subjectScoreMapper.selectList(queryWrapper);
        List listName = new ArrayList<>();
        List listScore = new ArrayList<>();
        if (list != null) {
            //获取学生姓名
            for (SubjectScoreDO name : list) {
                listName.add(name.getUsername());
            }
            map.put("x", listName);
            //获取学生成绩
            for (SubjectScoreDO score : list) {
                listScore.add(score.getScore());
            }
            map.put("y", listScore);
        } else {
            map.put("x", "无数据");
        }
        return map;
    }
}
