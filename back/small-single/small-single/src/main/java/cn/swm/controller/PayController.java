package cn.swm.controller;

import cn.swm.pojo.common.Result;
import cn.swm.service.OrderFrontService;
import cn.swm.service.OrderService;
import cn.swm.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {

    @Autowired
    private OrderFrontService orderFrontService;

    @RequestMapping(value = "/pay/pass",method = RequestMethod.GET)
    @ApiOperation(value = "支付审核通过")
    public Result<Object> payPass(String tokenName, String token, String id, String sendType){

        int result=orderFrontService.passPay(tokenName,token,id,sendType);
        if(result==-1){
            return new ResultUtil<Object>().setErrorMsg("无效的Token或链接");
        }
        if(result==0){
            return new ResultUtil<Object>().setErrorMsg("数据处理出错");
        }
        return new ResultUtil<Object>().setData("处理成功");
    }
}
