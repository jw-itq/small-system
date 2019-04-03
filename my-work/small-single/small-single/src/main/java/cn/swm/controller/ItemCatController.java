package cn.swm.controller;

import cn.swm.pojo.TbItemCat;
import cn.swm.pojo.common.Result;
import cn.swm.pojo.common.ZTreeNode;
import cn.swm.service.ItemCatService;
import cn.swm.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(value = "/item/cat/list",method = RequestMethod.GET)
    public List<ZTreeNode> getItemCatList(@RequestParam(name = "id",defaultValue = "0")int parentId){
        return itemCatService.getItemCatList(parentId);
    }

    @RequestMapping(value = "/item/cat/add",method = RequestMethod.POST)
    public Result<Object> addItemCat(@ModelAttribute TbItemCat tbItemCat){
        itemCatService.addItemCat(tbItemCat);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/item/cat/del/{id}",method = RequestMethod.DELETE)
    public Result<Object> deleteItemCat(@PathVariable("id")int id){
        itemCatService.deleteItemCat(id);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/item/cat/update",method = RequestMethod.POST)
    public Result<Object> updateItemCat(@ModelAttribute TbItemCat tbItemCat){
        itemCatService.updateItemCat(tbItemCat);
        return new ResultUtil<Object>().setData(null);
    }
}
