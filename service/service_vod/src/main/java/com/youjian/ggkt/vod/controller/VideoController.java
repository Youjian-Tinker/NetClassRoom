package com.youjian.ggkt.vod.controller;


import com.youjian.ggkt.model.vod.Video;
import com.youjian.ggkt.result.Result;
import com.youjian.ggkt.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author youjian
 * @since 2022-07-09
 */
@Api(tags = "小节")
@RestController
@RequestMapping("/admin/vod/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation("根据id查询")
    @GetMapping("get/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.ok(videoService.getById(id));
    }

    @ApiOperation("新增")
    @PostMapping("save")
    public Result save(@RequestBody Video video) {
        videoService.save(video);
        return Result.ok();
    }

    @ApiOperation("修改")
    @PostMapping("update")
    public Result update(@RequestBody Video video) {
        videoService.updateById(video);
        return Result.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        videoService.removeVideoById(id);
        return Result.ok();
    }
}

