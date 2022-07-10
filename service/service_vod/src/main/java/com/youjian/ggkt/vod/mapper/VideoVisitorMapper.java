package com.youjian.ggkt.vod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youjian.ggkt.model.vod.VideoVisitor;
import com.youjian.ggkt.vo.vod.VideoVisitorCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 视频来访者记录表 Mapper 接口
 * </p>
 *
 * @author youjian
 * @since 2022-07-10
 */
public interface VideoVisitorMapper extends BaseMapper<VideoVisitor> {

    List<VideoVisitorCountVo> findCount(@Param("courseId")Long courseId, @Param("startDate")String startDate, @Param("endDate")String endDate);
}
