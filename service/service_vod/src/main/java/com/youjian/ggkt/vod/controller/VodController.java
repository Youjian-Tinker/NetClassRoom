package com.youjian.ggkt.vod.controller;


import com.youjian.ggkt.exception.GgktException;
import com.youjian.ggkt.result.Result;
import com.youjian.ggkt.vod.service.VodService;
import com.youjian.ggkt.vod.utils.ConstantPropertiesUtil;
import com.youjian.ggkt.vod.utils.Signature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Api(tags = "腾讯云点播")
@RestController
@CrossOrigin
@RequestMapping("/vod")
public class VodController {

    @Autowired
    private VodService vodService;

    @ApiOperation("上传视频")
    @PostMapping("upload")
    public Result upload() {
        String fileId = vodService.uploadVideo();
        return Result.ok(fileId);
    }

    @ApiOperation("删除视频")
    @DeleteMapping("remove/{fileId}")
    public Result remove(@PathVariable String fileId) {
        vodService.removeVod(fileId);
        return Result.ok();
    }

    @ApiOperation("返回客户端签名")
    @PostMapping("sign")
    public Result sign() {
        Signature sign = new Signature();
        // 设置 App 的云 API 密钥
        sign.setSecretId(ConstantPropertiesUtil.ACCESS_KEY_ID);
        sign.setSecretKey(ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(java.lang.Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24 * 2); // 签名有效期：2天
        try {
            String signature = sign.getUploadSignature();
            System.out.println("signature : " + signature);
            return Result.ok(signature);
        } catch (Exception e) {
            System.out.print("获取签名失败");
            e.printStackTrace();
            throw new GgktException(20001, "获取签名失败");
        }
    }
}