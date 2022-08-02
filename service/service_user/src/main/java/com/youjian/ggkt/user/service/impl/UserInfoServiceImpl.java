package com.youjian.ggkt.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjian.ggkt.model.user.UserInfo;
import com.youjian.ggkt.user.mapper.UserInfoMapper;
import com.youjian.ggkt.user.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author youjian
 * @since 2022-08-02
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
