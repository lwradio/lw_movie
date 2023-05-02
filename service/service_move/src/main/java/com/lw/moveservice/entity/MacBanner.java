package com.lw.moveservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 广告表
 * </p>
 *
 * @author lw
 * @since 2023-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MacBanner对象", description="广告表")
public class MacBanner implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "banner_id", type = IdType.AUTO)
    private Integer bannerId;

    private String bannerTitle;

    private String bannerLink;

    private Integer bannerCat;

    private String bannerPic;

    private Long bannerStime;

    private Long bannerEtime;

    private Integer bannerType;

    private Integer bannerStatus;

    private Integer bannerOrder;


}
