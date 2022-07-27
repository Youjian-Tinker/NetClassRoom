package com.youjian.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youjian.ggkt.model.vod.Video;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author youjian
 * @since 2022-07-09
 */
public interface VideoService extends IService<Video> {

    void removeVideoById(Long id);

    void removeVideoByCourseId(Long id);
}
