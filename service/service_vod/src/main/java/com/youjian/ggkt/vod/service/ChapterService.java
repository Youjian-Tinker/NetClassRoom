package com.youjian.ggkt.vod.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youjian.ggkt.model.vod.Chapter;
import com.youjian.ggkt.vo.vod.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author youjian
 * @since 2022-07-09
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getTreeList(Long courseId);
}
