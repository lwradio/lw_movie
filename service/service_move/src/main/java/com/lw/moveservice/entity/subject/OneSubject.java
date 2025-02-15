package com.lw.moveservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubject {
    private Long typeId;

    private String typeName;
    //一个一级分类有多个二级分类
    private List<TwoSubject> children;
}
