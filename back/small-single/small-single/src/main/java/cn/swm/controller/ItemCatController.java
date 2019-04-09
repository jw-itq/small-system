package cn.swm.controller;

import cn.swm.pojo.TbItemCat;
import cn.swm.pojo.common.Result;
import cn.swm.pojo.common.ZTreeNode;
import cn.swm.pojo.front.IndexCat;
import cn.swm.service.ItemCatService;
import cn.swm.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //    首页分类导航
    @RequestMapping(value = "/item/catList",method = RequestMethod.GET)
    public Result<List<Map<String,List<IndexCat>>>> getIndexCategory(){
        List<Map<String,List<IndexCat>>> result = new ArrayList<>();


        //得到所有的父分类
        List<ZTreeNode> parentList = itemCatService.getItemCatList(0);
        //遍历所有的父分类
        for(ZTreeNode parent : parentList){
            if(parent.getName()==null||parent.getName()==""){
                continue;
            }
            Map<String,List<IndexCat>> map = new HashMap<>();
            List<IndexCat> indexCats = new ArrayList<>();
            //通过父分类再去查询子分类，以这个子分类作为小标题
            List<ZTreeNode> oneSon = itemCatService.getItemCatList(parent.getId());
            //如果子菜单少于7条就不显示了
            if(oneSon.size()<7){
                continue;
            }
            //然后再以这个小标题为父分类，取查询下面所有的分类，放入到indexCat里面的tags去
            for(ZTreeNode son1 : oneSon){
                IndexCat indexCat = new IndexCat();
                indexCat.setTitle(son1.getName());
                List<String> indexList = new ArrayList<>();
                List<ZTreeNode> twoSon = itemCatService.getItemCatList(son1.getId());
                for(ZTreeNode son2 : twoSon){
                    indexList.add(son2.getName());
                }
                indexCat.setTags(indexList);

                //在这里添加类
                indexCats.add(indexCat);
                map.put("classNav",indexCats);
            }
            //在这里添加集合
            if(map==null||map.size()<=0){
                continue;
            }
            result.add(map);
        }

        return new ResultUtil<List<Map<String,List<IndexCat>>>>().setData(result);
    }

    //首页导航主分类
    @RequestMapping(value = "/item/catMainList",method = RequestMethod.GET)
    public Result<List<List<String>>> getIndexMainCategory(){
        List<ZTreeNode> parentList = itemCatService.getItemCatList(0);
        List<List<String>> result = new ArrayList<>();
        for(ZTreeNode parent : parentList){
            List<ZTreeNode> oneSon = itemCatService.getItemCatList(parent.getId());
            //如果子菜单少于7条就不要显示了
            if(oneSon.size()<7){
                continue;
            }
            String[] str = parent.getName().split("、|\\,|\\.|\\#");
            List<String> list = new ArrayList<>();
            for(String s : str){
                list.add(s);
            }
            result.add(list);
        }
        return new ResultUtil<List<List<String>>>().setData(result);
    }
}
