package cn.swm.controller;

import cn.swm.pojo.TbExpress;
import cn.swm.pojo.TbOrder;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.common.Result;
import cn.swm.service.ExpressService;
import cn.swm.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpressController {

    @Autowired
    private ExpressService expressService;

    @RequestMapping(value = "/express/list",method = RequestMethod.GET)
    public DataTableResult getExpressList(){
        DataTableResult result = new DataTableResult();
        List<TbExpress> list = expressService.getExpressList();
        result.setData(list);
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "/express/del/{ids}",method = RequestMethod.GET)
    public Result<Object> deleteExpressById(@PathVariable int[] ids){
        for(int id : ids){
            expressService.deleteExpressById(id);
        }
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/express/add",method = RequestMethod.POST)
    public Result<Object> addExpress(@ModelAttribute TbExpress tbExpress){
        expressService.addExpress(tbExpress);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/express/update",method = RequestMethod.POST)
    public Result<Object> updateExpress(@ModelAttribute TbExpress tbExpress){
        expressService.updateExpress(tbExpress);
        return new ResultUtil<Object>().setData(null);
    }
}
