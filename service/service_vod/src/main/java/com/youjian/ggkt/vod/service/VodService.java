package com.youjian.ggkt.vod.service;

import java.util.Map;

public interface VodService {
    String uploadVideo();

    void removeVod(String fileId);

    Map<String,Object> getPlayAuth(Long courseId, Long videoId);
}
