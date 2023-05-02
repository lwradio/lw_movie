package com.lw.moveservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lw.moveservice.entity.MacType;
import com.lw.moveservice.entity.front.QueryMove;
import com.lw.moveservice.entity.subject.OneSubject;
import com.lw.moveservice.entity.subject.TwoSubject;
import com.lw.moveservice.mapper.MacTypeMapper;
import com.lw.moveservice.service.MacTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lw
 * @since 2022-07-22
 */
@Service
public class MacTypeServiceImpl extends ServiceImpl<MacTypeMapper, MacType> implements MacTypeService {

    @Override
    public List<OneSubject> getAllOneTwosubject() {
        //先查询所有一级分类
        QueryWrapper<MacType> queryWrapperOne = new QueryWrapper();
        queryWrapperOne.eq("type_pid","0");
        List<MacType> oneSubjectList = baseMapper.selectList(queryWrapperOne);

        //查询所有非二级分类
        QueryWrapper<MacType> queryWrapperTwo = new QueryWrapper<>();
        queryWrapperTwo.ne("type_pid","0");
        List<MacType> twoSubjectList = baseMapper.selectList(queryWrapperTwo);

        //创建最终list存储数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //开始封装

        for (int i = 0; i < oneSubjectList.size(); i++) {
            MacType OnemacType = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(OnemacType,oneSubject);

            //放入最终list中
            finalSubjectList.add(oneSubject);

            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                MacType TwomacType = twoSubjectList.get(j);
                if (TwomacType.getTypePid().equals(OnemacType.getTypeId())){
                    //为其子类
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(TwomacType,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }

            }
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }
}
