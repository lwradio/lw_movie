package com.lw.moveservice.controller.admin;


import com.lw.commonutils.R;
import com.lw.moveservice.entity.MacType;
import com.lw.moveservice.entity.subject.OneSubject;
import com.lw.moveservice.service.MacTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
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

    @ApiOperation("查询所有分类")
    @GetMapping
    public R getAllSubject() {
        List<OneSubject> list = macTypeService.getAllOneTwosubject();
        return R.ok().data("list", list);
    }

    @ApiOperation("根据id查询某一个分类信息")
    @GetMapping("/{id}")
    public R getSubject(@PathVariable Long id) {
        MacType macType = macTypeService.getById(id);
        return R.ok().data("type", macType);
    }

    @ApiOperation("查询某个一级分类id下的所有子分类")
    @GetMapping("{id}/subType")
    public R getSubTypeByParentId(@PathVariable Long id) {
        OneSubject type = macTypeService.getSubTypeByParentId(id);
        return R.ok().data("type", type);
    }

    @ApiOperation("新增分类及其子类")
    @PostMapping
    public R addType(@RequestBody MacType types) {
        macTypeService.addTypes(types);
        return R.ok();
    }

    @ApiOperation("在某个父类中新增子类分类")
    @PostMapping("{id}")
    public R addTypeByPid(@PathVariable Long id, @RequestBody MacType macType) {
        int count = macTypeService.addTypeByPid(id, macType);
        return count > 0 ? R.ok() : R.error();
    }

    @ApiOperation("根据id删除分类，如果为父类子类也会删除，如果为子类只删除子类")
    @DeleteMapping("{id}")
    public R deleteById(@PathVariable Long id) {
        int count = macTypeService.deleteById(id);
        return count > 0 ? R.ok() : R.error();
    }

    @ApiOperation("修改")
    @PutMapping("{id}")
    public R updateById(@PathVariable Long id, @RequestBody MacType macType) {
        int count = macTypeService.updateById(id, macType);
        return count > 0 ? R.ok() : R.error();
    }
}

