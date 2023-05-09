package com.lw.moveservice.controller.front;

import lombok.Data;

@Data
public class VodDTO {
    private Long vodId;
    private String vodName;
    private String vodSub;
    private String vodEn;
    private String vodYear;
    private String vodArea;

    private String vodLang;
    private Boolean vodStatus;
    private String vodActor;

    private String vodDirector;
    private int vodLevel;

}
