package com.youjian.ggkt.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjian.ggkt.activity.mapper.CouponInfoMapper;
import com.youjian.ggkt.activity.service.CouponInfoService;
import com.youjian.ggkt.activity.service.CouponUseService;
import com.youjian.ggkt.client.user.UserInfoFeignClient;
import com.youjian.ggkt.model.activity.CouponInfo;
import com.youjian.ggkt.model.activity.CouponUse;
import com.youjian.ggkt.model.user.UserInfo;
import com.youjian.ggkt.vo.activity.CouponUseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 优惠券信息 服务实现类
 * </p>
 *
 * @author youjian
 * @since 2022-08-02
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {

    @Autowired
    private UserInfoFeignClient userInfoFeignClient;
    @Autowired
    private CouponUseService couponUseService;

    @Override
    public IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo) {
        //获取条件
        Long couponId = couponUseQueryVo.getCouponId();
        String couponStatus = couponUseQueryVo.getCouponStatus();
        String getTimeBegin = couponUseQueryVo.getGetTimeBegin();
        String getTimeEnd = couponUseQueryVo.getGetTimeEnd();
        //封装条件
        QueryWrapper<CouponUse> wrapper = new QueryWrapper<>();
        wrapper.eq(!StringUtils.isEmpty(couponId), "coupon_id",couponId);
        wrapper.eq(!StringUtils.isEmpty(couponStatus), "coupon_status",couponStatus);
        wrapper.ge(!StringUtils.isEmpty(getTimeBegin), "get_time",getTimeBegin);
        wrapper.le(!StringUtils.isEmpty(getTimeEnd), "get_time",getTimeEnd);
        //调用方法查询
        IPage<CouponUse> page = couponUseService.page(pageParam, wrapper);

        //封装用户昵称和手机号
        List<CouponUse> couponUseList = page.getRecords();
        couponUseList.forEach(this::getUserInfoByCouponUse);
        return page;
    }

    //封装用户昵称和手机号
    private void getUserInfoByCouponUse(CouponUse couponUse) {
        Long userId = couponUse.getUserId();
        if(!StringUtils.isEmpty(userId)) {
            UserInfo userInfo = userInfoFeignClient.getById(userId);
            if(userInfo != null) {
                couponUse.getParam().put("nickName", userInfo.getNickName());
                couponUse.getParam().put("phone", userInfo.getPhone());
            }
        }
    }

    @Override
    public void updateCouponInfoUseStatus(Long couponUseId, Long orderId) {
        CouponUse couponUse = new CouponUse();
        couponUse.setId(couponUseId);
        couponUse.setOrderId(orderId);
        couponUse.setCouponStatus("1");
        couponUse.setUsingTime(new Date());
        couponUseService.updateById(couponUse);
    }
}
