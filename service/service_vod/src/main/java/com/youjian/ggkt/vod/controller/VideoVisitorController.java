package com.youjian.ggkt.vod.controller;


import com.youjian.ggkt.result.Result;
import com.youjian.ggkt.vod.service.VideoVisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 前端控制器
 * </p>
 *
 * @author youjian
 * @since 2022-07-10
 */
@Api(tags = "课程统计")
@RestController
@RequestMapping("/vod/videoVisitor")
public class VideoVisitorController {

    @Autowired
    private VideoVisitorService videoVisitorService;

    @ApiOperation("课程统计")
    @GetMapping("findCount/{courseId}/{startDate}/{endDate}")
    public Result findCount(@PathVariable Long courseId,
                            @PathVariable String startDate,
                            @PathVariable String endDate) {
        Map<String, Object> map = videoVisitorService.findCount(courseId, startDate, endDate);
        return Result.ok(map);
    }

}

