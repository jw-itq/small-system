package cn.swm.controller;

import cn.swm.common.constant.DictConstant;
import cn.swm.common.jedis.JedisClient;
import cn.swm.pojo.TbDict;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.common.Result;
import cn.swm.service.DictService;
import cn.swm.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DictController {

    @Autowired
    private DictService dictService;

    @Autowired
    private JedisClient jedisClient;

    @RequestMapping(value = "/dict/list",method = RequestMethod.GET)
    public DataTableResult getDictList(){
        DataTableResult result = new DataTableResult();
        List<TbDict> list = dictService.getDictList();
        result.setData(list);
        result.setSuccess(true);

        return result;
    }

    @RequestMapping(value = "/dict/update",method = RequestMethod.POST)
    public Result<Object> editDict(@ModelAttribute TbDict tbDict){
        dictService.editDict(tbDict);
        update();
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/dict/del/{ids}",method = RequestMethod.GET)
    public Result<Object> deleteDict(@PathVariable("ids")int[] ids){
        for(int id : ids){
            dictService.deleteDict(id);
        }
        update();
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/dict/stop/list",method = RequestMethod.GET)
    public DataTableResult getStopDictList(){
        DataTableResult result = new DataTableResult();
        List<TbDict> list = dictService.getStopDictList();
        result.setSuccess(true);
        result.setData(list);

        return result;
    }

    @RequestMapping(value = "/dict/add",method = RequestMethod.POST)
    public Result<Object> addDict(@ModelAttribute TbDict tbDict){
        dictService.addDict(tbDict);
        return new ResultUtil<Object>().setData(null);
    }


    private void update() {
        //更新词典标示
        jedisClient.set(DictConstant.LAST_MODIFIED,String.valueOf(System.currentTimeMillis()));
        jedisClient.set(DictConstant.ETAG,String.valueOf(System.currentTimeMillis()));
        //更新缓存
        jedisClient.del(DictConstant.EXT_KEY);
        jedisClient.del(DictConstant.STOP_KEY);
    }
}
