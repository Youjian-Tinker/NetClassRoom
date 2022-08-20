package com.youjian.ggkt.vod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youjian.ggkt.model.vod.Course;
import com.youjian.ggkt.vo.vod.CoursePublishVo;
import com.youjian.ggkt.vo.vod.CourseVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author youjian
 * @since 2022-07-09
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo selectCoursePublishVoById(Long id);

    CourseVo selectCourseVoById(Long id);
}
