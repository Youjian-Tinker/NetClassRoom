package com.youjian.ggkt.activity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youjian.ggkt.activity.mapper.CouponInfoMapper;
import com.youjian.ggkt.activity.service.CouponInfoService;
import com.youjian.ggkt.model.activity.CouponInfo;
import com.youjian.ggkt.model.activity.CouponUse;
import com.youjian.ggkt.vo.activity.CouponUseQueryVo;
import org.springframework.stereotype.Service;

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
    @Override
    public IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo) {
        return null;
    }
}
