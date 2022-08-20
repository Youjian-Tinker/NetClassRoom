package com.youjian.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjian.ggkt.model.vod.*;
import com.youjian.ggkt.vo.vod.*;
import com.youjian.ggkt.vod.mapper.CourseMapper;
import com.youjian.ggkt.vod.service.*;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Autowired
    private CourseDescriptionService courseDescriptionService;
    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private VideoService videoService;
    @Autowired
    private ChapterService chapterService;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveCourseInfo(CourseFormVo vo) {
        Course course = mapperFacade.map(vo, Course.class);
        baseMapper.insert(course);

        CourseDescription description = new CourseDescription();
        description.setCourseId(course.getId());
        description.setId(course.getId());
        description.setDescription(vo.getDescription());
        courseDescriptionService.save(description);
        return course.getId();
    }

    @Override
    public CourseFormVo getCourseInfoById(Long id) {
        Course course = baseMapper.selectById(id);
        if (course == null) {
            return null;
        }
        CourseDescription description = courseDescriptionService.getById(id);
        CourseFormVo courseFormVo = mapperFacade.map(course, CourseFormVo.class);
        if (description != null) {
            courseFormVo.setDescription(description.getDescription());
        }
        return courseFormVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCourseInfo(CourseFormVo vo) {
        Course course = mapperFacade.map(vo, Course.class);
        if (course.getId() == null) {
            return;
        }
        int i = baseMapper.updateById(course);
        CourseDescription description = new CourseDescription();
        description.setId(course.getId());
        description.setDescription(vo.getDescription());
        courseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getCoursePublishVo(Long id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public void publishCourse(Long id) {
        Course course = baseMapper.selectById(id);
        course.setStatus(1);
        course.setPublishTime(new Date());
        baseMapper.updateById(course);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCourse(Long id) {
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        videoService.remove(videoQueryWrapper);

        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        chapterService.remove(chapterQueryWrapper);

        courseDescriptionService.removeById(id);
        baseMapper.deleteById(id);
    }

    //课程列表
    @Override
    public Map<String,Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        //获取条件值
        String title = courseQueryVo.getTitle();//名称
        Long subjectId = courseQueryVo.getSubjectId();//二级分类
        Long subjectParentId = courseQueryVo.getSubjectParentId();//一级分类
        Long teacherId = courseQueryVo.getTeacherId();//讲师
        //封装条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(title), "title",title);
        wrapper.eq(subjectId != null, "subject_id",subjectId);
        wrapper.eq(subjectParentId != null, "subject_parent_id",subjectParentId);
        wrapper.eq(teacherId != null, "teacher_id",teacherId);

        //调用方法查询
        Page<Course> pages = baseMapper.selectPage(pageParam, wrapper);

        long totalCount = pages.getTotal();//总记录数
        long totalPage = pages.getPages();//总页数
        long currentPage = pages.getCurrent();//当前页
        long size = pages.getSize();//每页记录数
        //每页数据集合
        List<Course> records = pages.getRecords();
        records.forEach(this::getTeacherOrSubjectName);

        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",records);

        return map;
    }

    //获取讲师和分类名称
    private Course getTeacherOrSubjectName(Course course) {
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if(teacher != null) {
            course.getParam().put("teacherName",teacher.getName());
        }

        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if(subjectOne != null) {
            course.getParam().put("subjectParentTitle",subjectOne.getTitle());
        }
        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if(subjectTwo != null) {
            course.getParam().put("subjectTitle",subjectTwo.getTitle());
        }
        return course;
    }

    //根据id查询课程
    @Override
    public Map<String, Object> getInfoById(Long id) {
        //更新流量量
        Course course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);

        Map<String, Object> map = new HashMap<>();
        CourseVo courseVo = baseMapper.selectCourseVoById(id);
        List<ChapterVo> chapterVoList = chapterService.getTreeList(id);
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        Teacher teacher = teacherService.getById(course.getTeacherId());

        //TODO后续完善
        Boolean isBuy = false;

        map.put("courseVo", courseVo);
        map.put("chapterVoList", chapterVoList);
        map.put("description", null != courseDescription ?
                courseDescription.getDescription() : "");
        map.put("teacher", teacher);
        map.put("isBuy", isBuy);//是否购买
        return map;
    }
}
