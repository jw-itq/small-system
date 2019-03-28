package cn.swm.controller;

import cn.swm.pojo.TbPanelContent;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.common.Result;
import cn.swm.service.ContentService;
import cn.swm.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/content/list/{panelId}",method = RequestMethod.GET)
    public DataTableResult getContentById(@PathVariable("panelId")int panelId){
        DataTableResult result = contentService.getPanelContentListByPanelId(panelId);
        return result;
    }

    @RequestMapping(value = "/content/add",method = RequestMethod.POST)
    public Result<Object> addContent(@ModelAttribute TbPanelContent tbPanelContent){
        contentService.addContent(tbPanelContent);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/content/del/{ids}",method = RequestMethod.DELETE)
    public Result<Object> deletePanelContentById(@PathVariable int[] ids){
        //通过遍历这个数组来进行一个一个的删除
        for(int id : ids){
            contentService.deletePanelContent(id);
        }
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/content/update",method = RequestMethod.POST)
    public Result<Object> updatePanelContent(@ModelAttribute TbPanelContent tbPanelContent){
        contentService.updatePanelContent(tbPanelContent);
        return new ResultUtil<Object>().setData(null);
    }

}
