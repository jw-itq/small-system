package cn.swm.controller;

import cn.swm.pojo.TbBase;
import cn.swm.pojo.TbLog;
import cn.swm.pojo.TbShiroFilter;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.common.Result;
import cn.swm.service.SystemService;
import cn.swm.utils.ResultUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "/sys/base/update",method = RequestMethod.POST)
    public Result<Object> updateBase(@ModelAttribute TbBase tbBase){
        systemService.updateBase(tbBase);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/sys/shiro/list",method = RequestMethod.GET)
    public DataTableResult getShiroList(){
        DataTableResult result = new DataTableResult();
        List<TbShiroFilter> list = systemService.getShiroFilter();
        result.setData(list);

        return result;
    }

    @RequestMapping(value = "/sys/shiro/count",method = RequestMethod.GET)
    public Result<Object> getShiroCount(){
        int count = systemService.getShiroCount();
        return new ResultUtil<Object>().setData(count);
    }

    @RequestMapping(value = "/sys/shiro/add",method = RequestMethod.POST)
    public Result<Object> addShiro(@ModelAttribute TbShiroFilter tbShiroFilter){
        systemService.addShiro(tbShiroFilter);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/sys/shiro/del/{ids}",method = RequestMethod.GET)
    public Result<Object> deleteShiroById(@PathVariable("ids") int[] ids){
        for(int id : ids){
            systemService.deleteshiroById(id);
        }
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/sys/shiro/update",method = RequestMethod.POST)
    public Result<Object> updateShiro(@ModelAttribute TbShiroFilter tbShiroFilter){
        systemService.updateShiro(tbShiroFilter);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/sys/log",method = RequestMethod.GET)
    public DataTableResult getSysLogList(){
        DataTableResult result = new DataTableResult();
        List<TbLog> list = systemService.getLogList();
        result.setData(list);
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "/sys/log/count",method = RequestMethod.GET)
    public Result<Object> getLogCount(){
        int count = systemService.getLogCount();
        return new ResultUtil<Object>().setData(count);
    }

    @RequestMapping(value = "/sys/log/del/{ids}",method = RequestMethod.DELETE)
    public Result<Object> deleteLogById(@PathVariable("ids") int[] ids){
        for(int id : ids){
            systemService.deleteLogById(id);
        }
        return new ResultUtil<Object>().setData(null);
    }
}
