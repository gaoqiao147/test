package com.ecut.mapper;

import com.ecut.model.SubjectScoreDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhouwei
 * @since 2022-06-25
 */
@Mapper
public interface SubjectScoreMapper extends BaseMapper<SubjectScoreDO> {
    /**
     * 修改图片
     *
     * @param id
     * @param pic
     * @return
     */
    int updatePic(@Param("id") Integer id,@Param("pic") String pic);
}
