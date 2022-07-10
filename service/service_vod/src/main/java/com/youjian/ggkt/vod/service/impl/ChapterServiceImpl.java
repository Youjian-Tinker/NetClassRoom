package com.youjian.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjian.ggkt.model.vod.Chapter;
import com.youjian.ggkt.model.vod.Video;
import com.youjian.ggkt.vo.vod.ChapterVo;
import com.youjian.ggkt.vo.vod.VideoVo;
import com.youjian.ggkt.vod.mapper.ChapterMapper;
import com.youjian.ggkt.vod.service.ChapterService;
import com.youjian.ggkt.vod.service.VideoService;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author youjian
 * @since 2022-07-09
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    private VideoService videoService;
    @Autowired
    private MapperFacade mapperFacade;

    @Override
    public List<ChapterVo> getTreeList(Long courseId) {
        List<ChapterVo> result = new ArrayList<>();
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.orderByAsc("sort");
        List<Chapter> chapters = baseMapper.selectList(wrapper);

        LambdaQueryWrapper<Video> videoWrapper = new LambdaQueryWrapper<>();
        videoWrapper.eq(Video::getCourseId, courseId);
        List<Video> videoList = videoService.list(videoWrapper);
        Map<Long, List<Video>> videoListMap = videoList.stream().collect(Collectors.groupingBy(Video::getChapterId));

        chapters.forEach(chapter -> {
            ChapterVo vo = mapperFacade.map(chapter, ChapterVo.class);
            List<Video> videos = videoListMap.get(chapter.getId());
            if (CollectionUtils.isNotEmpty(videos)) {
                videos.sort(Comparator.comparing(Video::getSort));
                vo.setChildren(mapperFacade.mapAsList(videos, VideoVo.class));
            }
            result.add(vo);
        });

        return result;
    }
}
