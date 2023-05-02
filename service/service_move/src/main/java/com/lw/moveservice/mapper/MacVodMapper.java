package com.lw.moveservice.mapper;

import com.lw.moveservice.entity.MacVod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2022-07-21
 */
public interface MacVodMapper extends BaseMapper<MacVod> {
    int cancelLevel();
    //顶部幻灯片
    int updateTopLevel(String name);
    //12宫推荐
    int updateLevel(String name);

}
