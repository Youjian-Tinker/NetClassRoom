package com.youjian.ggkt.vod.controller;

import com.youjian.ggkt.result.Result;
import com.youjian.ggkt.vod.service.FIleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/vod/file")
public class FileUploadController {

    @Autowired
    private FIleService fIleService;

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public Result uploadFile(MultipartFile file) {
        String url = fIleService.upload(file);
        return Result.ok(url).message("上传文件成功");
    }

}
