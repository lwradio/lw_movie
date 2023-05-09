package com.lw.moveservice.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.lw.moveservice.entity.front.VodPlayer;
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
 * @since 2022-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MacVod对象", description="")
public class MacVod implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "vod_id", type = IdType.AUTO)
    private Long vodId;

    private Long typeId;

    @TableField(value ="type_id_1")
    private Long typeId1;

    private Long groupId;

    private String vodName;

    private String vodSub;

    private String vodEn;

    private Boolean vodStatus;

    private String vodLetter;

    private String vodColor;

    private String vodTag;

    private String vodClass;

    private String vodPic;

    private String vodPicThumb;

    private String vodPicSlide;

    private String vodPicScreenshot;

    private String vodActor;

    private String vodDirector;

    private String vodWriter;

    private String vodBehind;

    private String vodBlurb;

    private String vodRemarks;

    private String vodPubdate;

    private Integer vodTotal;

    private String vodSerial;

    private String vodTv;

    private String vodWeekday;

    private String vodArea;

    private String vodLang;

    private String vodYear;

    private String vodVersion;

    private String vodState;

    private String vodAuthor;

    private String vodJumpurl;

    private String vodTpl;

    private String vodTplPlay;

    private String vodTplDown;

    private int vodIsend;

    private int vodLock;

    private int vodLevel;

    private int vodCopyright;

    private Integer vodPoints;

    private Integer vodPointsPlay;

    private Integer vodPointsDown;

    private Integer vodHits;

    private Integer vodHitsDay;

    private Integer vodHitsWeek;

    private Integer vodHitsMonth;

    private String vodDuration;

    private Integer vodUp;

    private Integer vodDown;

    private BigDecimal vodScore;

    private Integer vodScoreAll;

    private Integer vodScoreNum;

    private Integer vodTime;

    private Integer vodTimeAdd;

    private Integer vodTimeHits;

    private Integer vodTimeMake;

    private Integer vodTrysee;

    private Integer vodDoubanId;

    private BigDecimal vodDoubanScore;

    private String vodReurl;

    private String vodRelVod;

    private String vodRelArt;

    private String vodPwd;

    private String vodPwdUrl;

    private String vodPwdPlay;

    private String vodPwdPlayUrl;

    private String vodPwdDown;

    private String vodPwdDownUrl;

    private String vodContent;

    private String vodPlayFrom;

    private String vodPlayServer;

    private String vodPlayNote;

    private String vodPlayUrl;

    private String vodDownFrom;

    private String vodDownServer;

    private String vodDownNote;

    private String vodDownUrl;

    private Boolean vodPlot;

    private String vodPlotName;

    private String vodPlotDetail;

    private int isDelete;
//格式化后的播放器列表
    @TableField(exist = false)
    private List<VodPlayer> playList;

}
