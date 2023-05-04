package com.lw.moveservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.moveservice.entity.MacType;
import com.lw.moveservice.entity.front.QueryMove;
import com.lw.moveservice.entity.subject.OneSubject;
import com.lw.moveservice.entity.subject.TwoSubject;
import com.lw.moveservice.mapper.MacTypeMapper;
import com.lw.moveservice.service.MacTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lw
 * @since 2022-07-22
 */
@Service
public class MacTypeServiceImpl extends ServiceImpl<MacTypeMapper, MacType> implements MacTypeService {

    @Override
    public int updateById(Long id, MacType type) {
        MacType macType = baseMapper.selectById(id);
        if (macType == null) {
            throw new RuntimeException("can not find id：" + id + " Type!");
        }
        macType.setTypeName(type.getTypeName());
        macType.setTypeDes(type.getTypeDes());
        return baseMapper.updateById(macType);
    }

    @Override
    public List<OneSubject> getAllOneTwosubject() {
        //先查询所有一级分类
        QueryWrapper<MacType> queryWrapperOne = new QueryWrapper();
        queryWrapperOne.eq("type_pid", "0");
        List<MacType> oneSubjectList = baseMapper.selectList(queryWrapperOne);

        //查询所有非二级分类
        QueryWrapper<MacType> queryWrapperTwo = new QueryWrapper<>();
        queryWrapperTwo.ne("type_pid", "0");
        List<MacType> twoSubjectList = baseMapper.selectList(queryWrapperTwo);

        //创建最终list存储数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //开始封装

        for (int i = 0; i < oneSubjectList.size(); i++) {
            MacType OnemacType = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(OnemacType, oneSubject);

            //放入最终list中
            finalSubjectList.add(oneSubject);

            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                MacType TwomacType = twoSubjectList.get(j);
                if (TwomacType.getTypePid().equals(OnemacType.getTypeId())) {
                    //为其子类
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(TwomacType, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }

            }
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }

    @Override
    public OneSubject getSubTypeByParentId(Long id) {
        MacType macType = baseMapper.selectById(id);
        if (macType == null) {
            return null;
        }
        OneSubject result = new OneSubject();
        List<TwoSubject> children = new ArrayList<>();
        result.setTypeName(macType.getTypeName());
        result.setTypeId(macType.getTypeId());
        QueryWrapper<MacType> query = new QueryWrapper();
        query.eq("type_pid", id);
        List<MacType> macTypes = baseMapper.selectList(query);
        macTypes.forEach(
                type -> {
                    TwoSubject twoSubject = new TwoSubject();
                    twoSubject.setTypeId(type.getTypeId());
                    twoSubject.setTypeName(type.getTypeName());
                    children.add(twoSubject);
                }
        );
        result.setChildren(children);
        return result;
    }

    @Override
    public void addTypes(MacType types) {
        //获取子类
        List<MacType> subTypes = types.getSubTypes();
        types.setTypePid(0L);
        int insert = baseMapper.insert(types);
        if (insert == 0) {
            throw new RuntimeException("insert type fail");
        }
        Long pId = types.getTypeId();
        subTypes.forEach(
                type -> {
                    type.setTypePid(pId);
                    baseMapper.insert(type);
                }
        );
    }

    @Override
    public int addTypeByPid(Long id, MacType macType) {
        QueryWrapper<MacType> query = new QueryWrapper<>();
        query.eq("type_id", id).eq("type_pid", "0");
        Integer count = baseMapper.selectCount(query);
        if (count <= 0) {
            throw new RuntimeException("can not find id：" + id + " Type!");
        }
        macType.setTypePid(id);
        return baseMapper.insert(macType);
    }

    @Override
    public int deleteById(Long id) {
        MacType macType = baseMapper.selectById(id);
        if (macType == null) {
            throw new RuntimeException("can not find id：" + id + " Type!");
        }
        Long typePid = macType.getTypePid();
        if (typePid.equals(0L)) {
            //删除其子类
            QueryWrapper<MacType> query = new QueryWrapper<>();
            query.eq("type_pid", id);
            baseMapper.delete(query);
        }
        return baseMapper.deleteById(id);
    }
}
