package com.ecut.service;

import com.ecut.model.SubjectScoreDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecut.vo.PageVo;
import com.ecut.vo.ResultVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhouwei
 * @since 2022-06-25
 */
public interface SubjectScoreService extends IService<SubjectScoreDO> {
    /**
     * 返回所有学生的所有成绩
     *
     * @return List数组
     */
    List<SubjectScoreDO> selectAllStudentScore();

    /**
     * 通过学期查询学生的成绩
     *
     * @param date 学期
     * @return
     */
    List<SubjectScoreDO> scoreByDate(String date);

    /**
     * 实现学生分数的分页展示
     *
     * @param pageVo 分页
     * @return
     */
    ResultVo studentScorePage(PageVo pageVo);

    /**
     * 实现学科的模糊查询
     *
     * @param subject
     * @return
     */
    List<SubjectScoreDO> scoreFuzzyQuery(String subject);

    /**
     * 实现每门科目平均分的计算
     *
     * @return map集合（科目，平均分）
     */
    Map<String,Double> avgSubjectScore();

    /**
     * 学生成绩echarts图数据
     *
     * @return 学生成绩map集合
     */
    Map<String,Object> stuScore();

    /**
     * 学生成绩echarts图数据 根据不同的学科展示不同的结果
     *
     * @param subject
     * @return
     */
    Map<String, Object> stuScoreBySubject(String subject);
}
