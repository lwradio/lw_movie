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
 * 
 * </p>
 *
 * @author lw
 * @since 2022-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MacType对象", description="")
public class MacType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "type_id", type = IdType.AUTO)
    private String typeId;

    private String typeName;

    private String typeEn;

    private Integer typeSort;

    private Integer typeMid;

    private String typePid;

    private Boolean typeStatus;

    private String typeTpl;

    private String typeTplList;

    private String typeTplDetail;

    private String typeTplPlay;

    private String typeTplDown;

    private String typeKey;

    private String typeDes;

    private String typeTitle;

    private String typeUnion;

    private String typeExtend;

    private String typeLogo;

    private String typePic;

    private String typeJumpurl;


}
