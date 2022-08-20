package com.youjian.ggkt.vod.controller;


import com.youjian.ggkt.model.vod.Subject;
import com.youjian.ggkt.result.Result;
import com.youjian.ggkt.vod.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author youjian
 * @since 2022-07-09
 */
@Api(tags = "课程科目")
@RestController
@RequestMapping("/admin/vod/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @ApiOperation("课程分类列表")
    @GetMapping("subjectList/{id}")
    public Result getSubjectList(@PathVariable Long id) {
        List<Subject> list = subjectService.selectSubjectList(id);
        return Result.ok(list);
    }

    @ApiOperation("excel导出课程")
    @GetMapping("export")
    public void export(HttpServletResponse response) {
        subjectService.exportData(response);
    }

    @ApiOperation("excel导入课程")
    @PostMapping("import")
    public Result importData(MultipartFile file) {
        subjectService.importData(file);
        return Result.ok();
    }
}

