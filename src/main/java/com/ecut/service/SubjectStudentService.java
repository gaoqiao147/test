package com.ecut.service;

import com.ecut.model.SubjectStudentDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhouwei
 * @since 2022-06-25
 */
public interface SubjectStudentService extends IService<SubjectStudentDO> {
    /**
     * 返回所有数据
     *
     * @return
     */
    List<SubjectStudentDO> allList() throws JsonProcessingException;
}
