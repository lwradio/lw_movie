package com.lw.moveservice.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.commonutils.R;
import com.lw.moveservice.entity.HitEnum;
import com.lw.moveservice.entity.MacVod;
import com.lw.moveservice.entity.VodHitsDTO;
import com.lw.moveservice.entity.front.LevelMovie;
import com.lw.moveservice.entity.front.QueryMove;
import com.lw.moveservice.entity.front.VodPlayer;
import com.lw.moveservice.service.MacVodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lw
 * @since 2022-07-21
 */
@RestController
@CrossOrigin
@RequestMapping("/movieservice/macvod")
public class MacVodController {
    @Autowired
    private MacVodService macVodService;


    //条件查询
    @ApiOperation("分页查询")
    @PostMapping
    public R pageMoveCondition(@RequestParam int page, @RequestParam int limit,
                               @RequestBody(required = false) QueryMove queryMove) {
        Map<String, Object> map = macVodService.pageMoveCondition(page, limit, queryMove);
        return R.ok().data(map);
    }

    @ApiOperation("根据视频id查询视频详情方法")
    @GetMapping("/{id}")
    public R getMoveInfo(@PathVariable Long id) {
        MacVod macVod = macVodService.selectByid(id);
        return R.ok().data("move", macVod);
    }

    @ApiOperation("根据视频id查询对应播放器和对应播放地址")
    @GetMapping("/{id}/Player")
    public R getMovePlayer(@PathVariable Long id) {
        List<VodPlayer> players = macVodService.getMovePlayer(id);
        return R.ok().data("player", players);
    }

    @ApiOperation("修改视频")
    @PutMapping("/{id}")
    //@RequestBody只能有一个,SpringMVC中@RequestBody是读取的流的方式,
    // 在取 body参数时第一个参数取到后把request.getInputStream()关闭，导致后面的@requestBody的对象拿取不到
    public R updateMove(@PathVariable Long id, @RequestBody MacVod macVod) {
        int flag = macVodService.updateMove(id, macVod);
        return flag > 0 ? R.ok() : R.error();
    }

    @ApiOperation("删除视频")
    @DeleteMapping("/{id}")
    public R deleteMove(@PathVariable Long id) {
        macVodService.deleteMove(id);
        return R.ok();
    }

    @ApiOperation("批量删除")
    @DeleteMapping("/ids")
    public R deleteMove(@RequestParam List<Long> ids) {
        List<MacVod> macVods = new ArrayList<>();
        ids.forEach(
                id -> {
                    MacVod macVod = new MacVod();
                    macVod.setVodId(id);
                    macVod.setIsDelete(1);
                    macVod.setVodStatus(false);
                    macVods.add(macVod);
                }
        );
        macVodService.updateBatchById(macVods);
        return R.ok();
    }

    @ApiOperation("爬取豆瓣热门榜单更新推荐")
    @PutMapping("/level")
    public R updateLevel() {
        boolean flag = macVodService.updateLevel();
        return flag ? R.ok() : R.error();
    }

//    @ApiOperation("爬取豆瓣热门榜单")
//    @GetMapping("/level")
//    public R getDouBan() {
//        boolean flag = macVodService.updateLevel();
//        return flag ? R.ok() : R.error();
//    }

    @ApiOperation("查询推荐的影视")
    @GetMapping("/level")
    public R getLevelMovie() {
        LevelMovie levelMovie = macVodService.getLevelMovie();
        return R.ok().data("levelMovie", levelMovie);
    }

    @ApiOperation("重复片名")
    @GetMapping("/sameVodName")
    public R getSameVodName(@RequestParam int page, @RequestParam int limit) {
        Map<String, Object> sameVodDTOS = macVodService.getsameVodName(page, limit);
        return R.ok().data("sameVodDTOS", sameVodDTOS);
    }

    @ApiOperation("重置热度")
    @PutMapping("/hit")
    public R reloadHit(@RequestParam(required = false) HitEnum type) {
        return R.ok().data("count", macVodService.reloadHit(type));
    }

    @ApiOperation("查询没有豆瓣id的影片")
    @GetMapping("/withOutDoubanId")
    public R withOutDoubanId(@RequestParam int page, @RequestParam int limit,
                             @RequestBody(required = false) QueryMove queryMove) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<MacVod> macVodPage = macVodService.withOutDoubanId(pageRequest, queryMove);
        return R.ok().data("count", macVodPage);
    }

    @ApiOperation("根据vodId数组获取对应豆瓣id并且填充")
    @PutMapping("/douBanId/ids")
    public R getDouBanIds(@RequestBody List<Long> ids) {
        macVodService.getDouBanIds(ids);
        return R.ok();
    }

    @ApiOperation("查询vod类型点击量排行榜")
    @GetMapping("/{typeId}/hit/rank")
    public R getHitRank(@RequestParam HitEnum type, @RequestParam(required = false) int limit,
                        @PathVariable Long typeId) {
        List<VodHitsDTO> vodHitsDTOS = macVodService.getHitRank(type, limit,typeId);
        return R.ok().data("data", vodHitsDTOS);
    }
}

