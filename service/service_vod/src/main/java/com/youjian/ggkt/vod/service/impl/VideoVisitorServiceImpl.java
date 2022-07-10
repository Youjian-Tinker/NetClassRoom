package com.youjian.ggkt.vod.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjian.ggkt.model.vod.VideoVisitor;
import com.youjian.ggkt.vo.vod.VideoVisitorCountVo;
import com.youjian.ggkt.vod.mapper.VideoVisitorMapper;
import com.youjian.ggkt.vod.service.VideoVisitorService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 视频来访者记录表 服务实现类
 * </p>
 *
 * @author youjian
 * @since 2022-07-10
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor> implements VideoVisitorService {

    @Override
    public Map<String, Object> findCount(Long courseId, String startDate, String endDate) {
        List<VideoVisitorCountVo> list = baseMapper.findCount(courseId, startDate, endDate);
        Map<String, Object> map = new HashMap<>();
        List<Integer> valueList = list.stream().map(VideoVisitorCountVo::getUserCount).collect(Collectors.toList());
        List<String> dateList = list.stream().map(VideoVisitorCountVo::getJoinTime).collect(Collectors.toList());
        map.put("xData", dateList);
        map.put("yData", valueList);
        return map;
    }
}
