package com.lw.moveservice.controller.front;


import com.lw.commonutils.R;
import com.lw.moveservice.entity.MacType;
import com.lw.moveservice.entity.subject.OneSubject;
import com.lw.moveservice.service.MacTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lw
 * @since 2022-07-22
 */
@RestController
@CrossOrigin
@RequestMapping("/moveservice/mactype")
public class MacTypeController {
    @Autowired
    private MacTypeService macTypeService;

    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = macTypeService.getAllOneTwosubject();
        return R.ok().data("list",list);
    }
    @PostMapping("getSubject/{id}")
    public R getSubject(@PathVariable String id){
        MacType macType = macTypeService.getById(id);
        return R.ok().data("type",macType);
    }

}

