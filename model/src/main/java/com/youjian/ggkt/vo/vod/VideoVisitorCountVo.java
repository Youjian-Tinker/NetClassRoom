package com.youjian.ggkt.vo.vod;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoVisitorCountVo {

	@ApiModelProperty(value = "进入时间")
	private String joinTime;

	@ApiModelProperty(value = "用户个数")
	private Integer userCount;


}

