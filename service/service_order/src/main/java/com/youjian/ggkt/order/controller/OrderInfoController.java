package com.youjian.ggkt.order.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youjian.ggkt.model.order.OrderInfo;
import com.youjian.ggkt.order.service.OrderInfoService;
import com.youjian.ggkt.result.Result;
import com.youjian.ggkt.vo.order.OrderInfoQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 订单表 订单表 前端控制器
 * </p>
 *
 * @author youjian
 * @since 2022-08-01
 */
@Api(tags = "订单")
@RestController
@RequestMapping("/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @PathVariable Long page,
            @PathVariable Long limit,
            @ApiParam(name = "orderInfoVo", value = "查询对象", required = false)
                    OrderInfoQueryVo orderInfoQueryVo) {
        Page<OrderInfo> pageParam = new Page<>(page, limit);
        Map<String,Object> map = orderInfoService.selectOrderInfoPage(pageParam, orderInfoQueryVo);
        return Result.ok(map);
    }

}

