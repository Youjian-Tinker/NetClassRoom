package com.youjian.ggkt.wechat.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youjian.ggkt.model.wechat.Menu;
import com.youjian.ggkt.vo.wechat.MenuVo;

import java.util.List;

/**
 * <p>
 * 订单明细 订单明细 服务类
 * </p>
 *
 * @author youjian
 * @since 2022-08-08
 */
public interface MenuService extends IService<Menu> {

    List<MenuVo> findMenuInfo();

    List<Menu> findMenuOneInfo();

    void syncMenu();

    void removeMenu();
}
