package com.lw.moveservice.entity.front;

import lombok.Data;

import java.util.List;

@Data
public class LevelMovie {

    private int topCount;

    private int lowCount;

    private List<VodDTO> top;

    private List<VodDTO> low;

}
