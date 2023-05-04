package com.lw.moveservice.service;

import com.lw.moveservice.entity.MacType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lw.moveservice.entity.subject.OneSubject;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lw
 * @since 2022-07-22
 */
public interface MacTypeService extends IService<MacType> {

    int updateById(Long id, MacType macType);

    List<OneSubject> getAllOneTwosubject();

    OneSubject getSubTypeByParentId(Long id);

    void addTypes(MacType types);

    int addTypeByPid(Long id, MacType macType);

    int deleteById(Long id);
}
