package com.youjian.ggkt.vod.controller;


import com.youjian.ggkt.model.vod.Chapter;
import com.youjian.ggkt.result.Result;
import com.youjian.ggkt.vo.vod.ChapterVo;
import com.youjian.ggkt.vod.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author youjian
 * @since 2022-07-09
 */
@Api(tags = "章节")
@RestController
@CrossOrigin
@RequestMapping("/vod/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("章节列表")
    @GetMapping("getNestedTreeList/{courseId}")
    public Result list(@PathVariable Long courseId) {
        List<ChapterVo> list = chapterService.getTreeList(courseId);
        return Result.ok(list);
    }

    @ApiOperation("添加章节")
    @PostMapping("save")
    public Result save(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return Result.ok();
    }

    @ApiOperation("根据id查询章节")
    @GetMapping("get/{id}")
    public Result getById(@PathVariable Long id) {
        Chapter chapter = chapterService.getById(id);
        return Result.ok(chapter);
    }


    @ApiOperation("修改章节")
    @PostMapping("update")
    public Result update(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return Result.ok();
    }

    @ApiOperation("删除章节")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        chapterService.removeById(id);
        return Result.ok();
    }

}

