package com.lw.moveservice.entity;

import lombok.Data;

/**
 * @Author: lw
 * @Date: 2023/10/12/14:12
 * @Description:
 */
@Data
public class VodHitsDTO {

    private Long vodId;

    private String vodName;

    private Long typeId;

    private Long typeId1;

    private String vodActor;

    private String vodDirector;

    private String vodArea;

    private String vodLang;

    private String vodYear;

    private Integer vodHits;

    private Integer vodHitsDay;

    private Integer vodHitsWeek;

    private Integer vodHitsMonth;

}
