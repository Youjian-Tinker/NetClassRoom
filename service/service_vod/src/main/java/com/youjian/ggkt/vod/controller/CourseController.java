package com.youjian.ggkt.vod.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youjian.ggkt.model.vod.Course;
import com.youjian.ggkt.result.Result;
import com.youjian.ggkt.vo.vod.CourseFormVo;
import com.youjian.ggkt.vo.vod.CoursePublishVo;
import com.youjian.ggkt.vo.vod.CourseQueryVo;
import com.youjian.ggkt.vod.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author youjian
 * @since 2022-07-09
 */
@Api(tags = "课程")
@RestController
@RequestMapping("/admin/vod/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @ApiOperation("点播课程列表")
    @GetMapping("list/{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       CourseQueryVo courseQueryVo) {
        Page<Course> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.findPageCourse(pageParam, courseQueryVo);
        return Result.ok(map);
    }

    @ApiOperation("添加课程信息")
    @PostMapping("save")
    public Result save(@RequestBody CourseFormVo vo) {
        Long courseId = courseService.saveCourseInfo(vo);
        return Result.ok(courseId);
    }

    @ApiOperation("根据id获取课程信息")
    @GetMapping("get/{id}")
    public Result findById(@PathVariable Long id) {
        CourseFormVo courseInfo = courseService.getCourseInfoById(id);
        return Result.ok(courseInfo);
    }

    @ApiOperation("修改课程信息")
    @PostMapping("update")
    public Result update(@RequestBody CourseFormVo vo) {
        courseService.updateCourseInfo(vo);
        return Result.ok(vo.getId());
    }

    @ApiOperation("根据id查询课程发布信息")
    @GetMapping("getCoursePublishVo/{id}")
    public Result getCoursePublishVo(@PathVariable Long id) {
        CoursePublishVo result = courseService.getCoursePublishVo(id);
        return Result.ok(result);
    }

    @ApiOperation("课程最终发布")
    @PutMapping("publishCourseById/{id}")
    public Result publishCourse(@PathVariable Long id) {
        courseService.publishCourse(id);
        return Result.ok();
    }

    @ApiOperation("删除课程")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        courseService.removeCourse(id);

        return Result.ok();
    }
}

