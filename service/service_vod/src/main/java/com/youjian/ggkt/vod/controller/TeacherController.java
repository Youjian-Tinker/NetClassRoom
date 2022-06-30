package com.youjian.ggkt.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youjian.ggkt.model.vod.Teacher;
import com.youjian.ggkt.result.Result;
import com.youjian.ggkt.vo.vod.TeacherQueryVo;
import com.youjian.ggkt.vod.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author youjian
 * @since 2022-06-29
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping("/vod/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("查询所有讲师")
    @GetMapping("findAll")
    public Result<List<Teacher>> findAll() {
        return Result.ok(teacherService.list());
    }

    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("remove/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean success = teacherService.removeById(id);
        return success ? Result.ok() : Result.fail("删除失败");
    }

    @ApiOperation("分页条件查询")
    @PostMapping("findPage/{current}/{limit}")
    public Result<Page<Teacher>> findPage(@PathVariable Long current,
                                          @PathVariable Long limit,
                                          @RequestBody(required = false) TeacherQueryVo vo) {
        Page<Teacher> page = new Page<>(current, limit);
        if (vo == null) {
            return Result.ok(teacherService.page(page, null));
        } else {
            QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(vo.getName())) {
                wrapper.like("name", vo.getName());
            }
            if (vo.getLevel() != null) {
                wrapper.eq("level", vo.getLevel());
            }
            if (!StringUtils.isEmpty(vo.getJoinDateBegin())) {
                wrapper.ge("join_date", vo.getJoinDateBegin());
            }
            if (!StringUtils.isEmpty(vo.getJoinDateEnd())) {
                wrapper.le("join_date", vo.getJoinDateEnd());
            }

            return Result.ok(teacherService.page(page, wrapper));
        }
    }

    @ApiOperation("添加讲师")
    @PostMapping("save")
    public Result save(@RequestBody Teacher teacher) {
        return teacherService.save(teacher) ? Result.ok() : Result.fail("添加失败");
    }
    @ApiOperation("根据ID查询")
    @GetMapping("findById/{id}")
    public Result<Teacher> findById(@PathVariable Long id) {
        return Result.ok(teacherService.getById(id));
    }

    @ApiOperation("修改")
    @PostMapping("update")
    public Result update(@RequestBody Teacher teacher) {
        return teacherService.updateById(teacher) ? Result.ok() : Result.fail();
    }

    @ApiOperation("批量删除")
    @DeleteMapping("batchDelete")
    public Result batchDelete(@RequestBody List<Long> idList) {
        return teacherService.removeByIds(idList) ? Result.ok() : Result.fail();
    }
}

