package cn.swm.controller;

import cn.swm.pojo.TbExpress;
import cn.swm.pojo.TbOrder;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.common.Result;
import cn.swm.service.ExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
