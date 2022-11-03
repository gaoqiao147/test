package com.ecut.mapper;

import com.ecut.model.SubjectStudentDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhouwei
 * @since 2022-06-25
 */
public interface SubjectStudentMapper extends BaseMapper<SubjectStudentDO> {
    /**
     * 返回所有数据
     *
     * @return
     */
    List<SubjectStudentDO> allList();
}
