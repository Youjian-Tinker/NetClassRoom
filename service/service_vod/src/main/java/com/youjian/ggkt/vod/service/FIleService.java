package com.youjian.ggkt.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface FIleService {
    String upload(MultipartFile file);
}
