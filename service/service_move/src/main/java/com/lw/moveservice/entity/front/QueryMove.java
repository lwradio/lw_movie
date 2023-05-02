package com.lw.moveservice.entity.front;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryMove {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "电影名称")
    private String vodName;
    @ApiModelProperty(value = "推荐")
    private String vodLevel;
    @ApiModelProperty(value = "一级类别id")
    private String typeIdParent;
    @ApiModelProperty(value = "二级类别id")
    private String typeId;
    @ApiModelProperty(value = "播放平台")
    private String vodPlayForm;
}
