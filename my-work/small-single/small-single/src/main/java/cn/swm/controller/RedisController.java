package cn.swm.controller;

import cn.swm.pojo.common.Result;
import cn.swm.service.ContentService;
import cn.swm.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/redis/index/list",method = RequestMethod.GET)
    public Result<Object> getIndexRedis(){
        String json = contentService.getIndexRedis();
        return new ResultUtil<Object>().setData(json);
    }

    @RequestMapping(value = "/redis/index/update",method = RequestMethod.GET)
    public Result<Object> updateIndexRedis(){
        contentService.updateIndexRedis();
        return new ResultUtil<Object>().setData(null);
    }
}
