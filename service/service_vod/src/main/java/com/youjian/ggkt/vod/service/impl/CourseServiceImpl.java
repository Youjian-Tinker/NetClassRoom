package com.youjian.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjian.ggkt.model.vod.Course;
import com.youjian.ggkt.model.vod.Subject;
import com.youjian.ggkt.model.vod.Teacher;
import com.youjian.ggkt.vo.vod.CourseQueryVo;
import com.youjian.ggkt.vod.mapper.CourseMapper;
import com.youjian.ggkt.vod.service.CourseService;
import com.youjian.ggkt.vod.service.SubjectService;
import com.youjian.ggkt.vod.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author youjian
 * @since 2022-07-09
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SubjectService subjectService;

    @Override
    public Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo vo) {
        Long subjectId = vo.getSubjectId();
        Long subjectParentId = vo.getSubjectParentId();
        String title = vo.getTitle();
        Long teacherId = vo.getTeacherId();
        QueryWrapper<Course> wrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(title)) {
            wrapper.like("title", title);
        }
        if (subjectId != null) {
            wrapper.eq("subject_id", subjectId);
        }
        if (subjectParentId != null) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (teacherId != null) {
            wrapper.eq("teacher_id", teacherId);
        }
        Page<Course> coursePage = baseMapper.selectPage(pageParam, wrapper);
        List<Course> records = coursePage.getRecords();

        records.forEach(this::convertId2Detail);

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount",coursePage.getTotal());
        map.put("totalPage",coursePage.getPages());
        map.put("records",records);
        return map;
    }

    private void convertId2Detail(Course course) {
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if (teacher != null) {
            course.getParam().put("teacherName", teacher.getName());
        }
        Subject subject = subjectService.getById(course.getSubjectParentId());
        if (subject != null) {
            course.getParam().put("subjectParentTitle", subject.getTitle());
        }
        Subject subject2 = subjectService.getById(course.getSubjectId());
        if (subject2 != null) {
            course.getParam().put("subjectTitle", subject2.getTitle());
        }
    }
}
