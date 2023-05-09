package com.lw.moveservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.moveservice.controller.admin.VodDTO;
import com.lw.moveservice.entity.MacVod;
import com.lw.moveservice.entity.front.LevelMovie;
import com.lw.moveservice.entity.front.QueryMove;
import com.lw.moveservice.entity.front.VodPlayer;
import com.lw.moveservice.mapper.MacVodMapper;
import com.lw.moveservice.service.MacVodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lw.servicebase.config.douban.DouBanConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lw
 * @since 2022-07-21
 */
@Service
public class MacVodServiceImpl extends ServiceImpl<MacVodMapper, MacVod> implements MacVodService {

    //条件查询带分页
    @Override
    public Map<String, Object> pageMoveCondition(int page, int limit, QueryMove queryMove) {
        //使用json传递数据，把json数据封装到对应对象
        //创建page对象
        Page<MacVod> macVodPage = new Page<>(page, limit);
        QueryWrapper<MacVod> wrapper = new QueryWrapper<>();
        //判断条件是否为空
        if (queryMove == null) {
            System.out.println("空");
        } else {
            String title = queryMove.getVodName();
            String typeId = queryMove.getTypeId();//二级类型
            String typeId_1 = queryMove.getTypeIdParent();//一级分类
            String vodLevel = queryMove.getVodLevel();

            if (!StringUtils.isEmpty(title)) {
                wrapper.like("vod_name", title);
            }
            if (!StringUtils.isEmpty(typeId)) {
                wrapper.eq("type_id", typeId);
            }
            if (!StringUtils.isEmpty(typeId_1)) {
                wrapper.eq("type_id_1", typeId_1);
            }
            if (!StringUtils.isEmpty(vodLevel)) {
                wrapper.eq("vod_level", vodLevel);
            }
        }
        wrapper.orderByDesc("vod_time");
        //调用方法实现条件查询分页
        baseMapper.selectPage(macVodPage, wrapper);
        long current = macVodPage.getCurrent();
        long pages = macVodPage.getPages();
        long size = macVodPage.getSize();
        long total = macVodPage.getTotal();
        boolean hasNext = macVodPage.hasNext();
        boolean hasPrevious = macVodPage.hasPrevious();
        List<MacVod> records = macVodPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;

    }

    @Override
    public MacVod selectByid(Long id) {
        MacVod macVod = baseMapper.selectById(id);
        macVod.setPlayList(getMovePlayer(macVod));
        return macVod;
    }

    @Override
    public List<VodPlayer> getMovePlayer(Long id) {
        MacVod macVod = baseMapper.selectById(id);
        return getMovePlayer(macVod);
    }

    public List<VodPlayer> getMovePlayer(MacVod macVod) {
        String playFrom = macVod.getVodPlayFrom();
        String vodPlayUrl = macVod.getVodPlayUrl();
        List<VodPlayer> list = new ArrayList<>();
        String[] splitFrom = playFrom.split("\\$\\$\\$");
        String[] splitUrl = vodPlayUrl.split("\\$\\$\\$");
        for (int i = 0; i < splitFrom.length; i++) {
            VodPlayer player = new VodPlayer();
            player.setPlayerName(splitFrom[i]);
            player.setPlayUrl(splitUrl[i].replace('#', '\n'));
            player.setPlayUrls(splitUrl[i].split("#"));
            list.add(player);

        }
        return list;
    }

    //更新幻灯片推荐
    @Override
    public boolean updateLevel() {
        List<String> move = null;
        List<String> tv = null;
        //先将之前的推荐清除
        baseMapper.cancelLevel();
        try {
            //前6条为幻灯片，后6条为推荐
            move = DouBanConfig.startLoad("move", "12", "0");
            tv = DouBanConfig.startLoad("tv", "12", "0");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (move == null || tv == null) {
            return false;
        }
        for (int i = 0; i < move.size(); i++) {
            if (i <= 5) {
                baseMapper.updateTopLevel(move.get(i));
                baseMapper.updateTopLevel(tv.get(i));
            } else {
                baseMapper.updateLevel(move.get(i));
                baseMapper.updateLevel(tv.get(i));
            }

        }
        return true;
    }

    //更新电影信息
    @Override
    public int updateMove(Long id, MacVod macVod) {
        String[] strings = rePlayUrl(macVod.getPlayList());
        macVod.setVodPlayFrom(strings[0]);
        macVod.setVodPlayUrl(strings[1]);
        //更新时间
        // 时间戳转换
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            macVod.setVodTime((int) (date.getTime() / 1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        macVod.setVodId(id);
        return baseMapper.updateById(macVod);
    }

    //删除一条记录
    @Override
    public void deleteMove(Long id) {
        baseMapper.deleteById(id);
    }

    @Override
    public LevelMovie getLevelMovie() {
        QueryWrapper<MacVod> wrapperTop = new QueryWrapper<>();
        QueryWrapper<MacVod> wrapper = new QueryWrapper<>();
        wrapperTop.eq("vod_level", 9);
        wrapper.eq("vod_level", 1);
        List<MacVod> macVods = baseMapper.selectList(wrapper);
        List<MacVod> macVodsTop = baseMapper.selectList(wrapperTop);
        List<VodDTO> vodDTOSTop = macVodsTop.stream()
                .map(
                        vod -> {
                            VodDTO vodDTO = new VodDTO();
                            BeanUtils.copyProperties(vod, vodDTO);
                            vodDTO.setPlayList(getMovePlayer(vod));
                            return vodDTO;
                        }
                ).collect(Collectors.toList());
        List<VodDTO> vodDTOS = macVods.stream()
                .map(
                        vod -> {
                            VodDTO vodDTO = new VodDTO();
                            BeanUtils.copyProperties(vod, vodDTO);
                            vodDTO.setPlayList(getMovePlayer(vod));
                            return vodDTO;
                        }
                ).collect(Collectors.toList());
        LevelMovie levelMovie = new LevelMovie();
        levelMovie.setTop(vodDTOSTop);
        levelMovie.setTopCount(vodDTOSTop.size());
        levelMovie.setLow(vodDTOS);
        levelMovie.setLowCount(vodDTOS.size());
        return levelMovie;
    }

    @Override
    public Map<String, Object> getsameVodName(int page, int limit) {
        Page<MacVod> pg = new Page<>(page, limit);
        Page<MacVod> macVodPage = baseMapper.getsameVodName(pg);
        List<MacVod> macVods = macVodPage.getRecords();
        if (macVods == null || macVods.isEmpty()) {
            return null;
        }
        Map<String, List<VodDTO>> items = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        for (MacVod macVod : macVods) {
            List<VodDTO> samVods = items.getOrDefault(macVod.getVodName(), new ArrayList<>());
            VodDTO vodDTO = new VodDTO();
            BeanUtils.copyProperties(macVod, vodDTO);
            vodDTO.setPlayList(getMovePlayer(macVod));
            samVods.add(vodDTO);
            items.put(macVod.getVodName(), samVods);
        }
        result.put("current", macVodPage.getCurrent());
        result.put("pages", macVodPage.getPages());
        result.put("size", macVodPage.getSize());
        result.put("total", macVodPage.getTotal());
        result.put("hasNext", macVodPage.hasNext());
        result.put("hasPrevious", macVodPage.hasPrevious());
        result.put("records", items);
        return result;
    }

    //对前端的播放器字符串与地址字符串进行格式化：播放器之间用$$$隔离储存，url地址中每个播放器地址间用$$$隔离，其中每集用#隔离储存
    public String[] rePlayUrl(List<VodPlayer> list) {
        String[] re = new String[2];
        StringBuilder url = new StringBuilder();
        StringBuilder from = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            VodPlayer player = list.get(i);
            if (player.getPlayerName() != null && player.getPlayUrl() != null) {
                from.append(player.getPlayerName().replace(" ", ""));//处理空格
                //前端为每集换行，把换行换为#隔离每集
                url.append(player.getPlayUrl().replace(" ", "").replace("\n", "#"));
                //如果是最后一条数据就不要加$$$
            }
            if (i != (list.size() - 1)) {
                from.append("$$$");
                url.append("$$$");
            }
        }
        re[0] = from.toString();
        re[1] = url.toString();
        return re;
    }
}
