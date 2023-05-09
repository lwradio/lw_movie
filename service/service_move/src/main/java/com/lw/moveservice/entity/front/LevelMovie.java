package com.lw.moveservice.entity.front;

import com.lw.moveservice.controller.front.VodDTO;
import lombok.Data;

import java.util.List;

@Data
public class LevelMovie {
    private int topCount;
    private int lowCount;
    private List<VodDTO> top;
    private List<VodDTO> low;

}
