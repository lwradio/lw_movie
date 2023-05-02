package com.lw.moveservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lw.commonutils.R;
import com.lw.moveservice.entity.MacVod;
import com.lw.moveservice.entity.front.QueryMove;
import com.lw.moveservice.entity.front.VodPlayer;
import com.lw.moveservice.service.MacVodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lw
 * @since 2022-07-21
 */
@RestController
@CrossOrigin
@RequestMapping("/moveservice/macvod")
public class MacVodController {
    @Autowired
    private MacVodService macVodService;
    //电影列表
    @GetMapping()
    public R getMoveList(){
        QueryWrapper<MacVod> wrapper = new QueryWrapper<>();
        wrapper.last("limit 5");
        List<MacVod> list = macVodService.list(wrapper);
        return R.ok().data("list",list);
    }



    //条件查询
    @PostMapping("pageMoveCondition/{page}/{limit}")
    public R pageMoveCondition(@PathVariable long page, @PathVariable long limit,
                               @RequestBody(required = false) QueryMove queryMove){
        Map<String,Object> map = macVodService.pageMoveCondition(page,limit,queryMove);
        return R.ok().data(map);
    }

    //根据视频id查询视频详情方法
    @PostMapping("getMoveInfo/{id}")
    public R getMoveInfo(@PathVariable String id){
        MacVod macVod = macVodService.selectByid(id);
            return R.ok().data("move",macVod);
    }

    //根据视频id查询对应播放器和对应播放地址
    @GetMapping("getMovePlayer/{id}")
    public R getMovePlayer(@PathVariable String id){
        List<VodPlayer> players = macVodService.getMovePlayer(id);
        return R.ok().data("player",players);
    }
    //修改视频
    @PostMapping("updateMove")
    //@RequestBody只能有一个,SpringMVC中@RequestBody是读取的流的方式,
    // 在取 body参数时第一个参数取到后把request.getInputStream()关闭，导致后面的@requestBody的对象拿取不到
    public R updateMove(@RequestBody MacVod macVod){
        int flag = macVodService.updateMove(macVod);
        return flag>0?R.ok():R.error();
    }
    @DeleteMapping("deleteMove/{id}")
    public R deleteMove(@PathVariable String id){
        macVodService.deleteMove(id);
        return R.ok();
    }
    //爬取豆瓣热门榜单更新推荐
    @GetMapping("updateLevel")
    public R updateLevel(){
        boolean flage = macVodService.updateLevel();
        return flage?R.ok():R.error();
    }
}

