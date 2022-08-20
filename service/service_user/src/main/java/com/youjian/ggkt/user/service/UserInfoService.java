package com.youjian.ggkt.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youjian.ggkt.model.user.UserInfo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author youjian
 * @since 2022-08-02
 */
public interface UserInfoService extends IService<UserInfo> {

    UserInfo getByOpenid(String openId);
}
