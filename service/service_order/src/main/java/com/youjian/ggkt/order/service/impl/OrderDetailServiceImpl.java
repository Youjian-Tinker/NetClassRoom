package com.youjian.ggkt.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjian.ggkt.model.order.OrderDetail;
import com.youjian.ggkt.order.mapper.OrderDetailMapper;
import com.youjian.ggkt.order.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细 订单明细 服务实现类
 * </p>
 *
 * @author youjian
 * @since 2022-08-01
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
