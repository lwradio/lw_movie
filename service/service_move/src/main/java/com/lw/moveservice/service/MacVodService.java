package com.lw.moveservice.service;

import com.lw.moveservice.entity.MacVod;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lw.moveservice.entity.front.QueryMove;
import com.lw.moveservice.entity.front.VodPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lw
 * @since 2022-07-21
 */
public interface MacVodService extends IService<MacVod> {

    Map<String, Object> pageMoveCondition(long page, long limit, QueryMove queryMove);

    MacVod selectByid(String id);

    List<VodPlayer> getMovePlayer(String id);

    boolean updateLevel();

    int updateMove(MacVod macVod);

    void deleteMove(String id);
}
