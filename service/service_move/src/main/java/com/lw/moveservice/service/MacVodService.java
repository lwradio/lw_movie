package com.lw.moveservice.service;


import com.lw.moveservice.entity.MacVod;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lw.moveservice.entity.front.LevelMovie;
import com.lw.moveservice.entity.front.QueryMove;
import com.lw.moveservice.entity.front.VodPlayer;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lw
 * @since 2022-07-21
 */
public interface MacVodService extends IService<MacVod> {

    Map<String, Object> pageMoveCondition(int page, int limit, QueryMove queryMove);

    MacVod selectByid(Long id);

    List<VodPlayer> getMovePlayer(Long Id);

    boolean updateLevel();

    int updateMove(Long id, MacVod macVod);

    void deleteMove(Long id);

    LevelMovie getLevelMovie();

    Map<String, Object> getsameVodName(int page, int limit);
}
