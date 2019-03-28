package cn.swm.controller;

import cn.swm.pojo.TbBase;
import cn.swm.pojo.common.Result;
import cn.swm.service.SystemService;
import cn.swm.utils.ResultUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "系统配置管理")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/sys/base",method = RequestMethod.GET)
    public Result<TbBase> getBase(){
        TbBase tbBase = systemService.getBase();
        return new ResultUtil<TbBase>().setData(tbBase);
    }
}
