package com.lw.moveservice.service;

import com.lw.moveservice.entity.MacType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lw.moveservice.entity.subject.OneSubject;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lw
 * @since 2022-07-22
 */
public interface MacTypeService extends IService<MacType> {

    List<OneSubject> getAllOneTwosubject();
}
