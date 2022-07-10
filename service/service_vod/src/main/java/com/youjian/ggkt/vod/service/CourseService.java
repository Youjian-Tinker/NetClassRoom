package com.youjian.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youjian.ggkt.model.vod.Course;
import com.youjian.ggkt.vo.vod.CourseFormVo;
import com.youjian.ggkt.vo.vod.CoursePublishVo;
import com.youjian.ggkt.vo.vod.CourseQueryVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author youjian
 * @since 2022-07-09
 */
public interface CourseService extends IService<Course> {

    Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    Long saveCourseInfo(CourseFormVo vo);

    CourseFormVo getCourseInfoById(Long id);

    void updateCourseInfo(CourseFormVo vo);

    CoursePublishVo getCoursePublishVo(Long id);

    void publishCourse(Long id);

    void removeCourse(Long id);
}
