package com.lw.moveservice.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.moveservice.entity.MacVod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import javax.crypto.Mac;
import java.util.List;

/**
 * <p>
 * Mapper 接口
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

    Page<MacVod> getsameVodName(Page<MacVod> page);
}
