package cn.swm.controller;

import cn.swm.pojo.TbPanel;
import cn.swm.pojo.common.Result;
import cn.swm.pojo.common.ZTreeNode;
import cn.swm.service.PanelService;
import cn.swm.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PanelController {

    @Autowired
    private PanelService panelService;

//   首页板块管理

    @RequestMapping(value = "/panel/indexAll/list",method = RequestMethod.GET)
    public List<ZTreeNode> getAllIndexPanel(){
        List<ZTreeNode> list = panelService.getPanelList(0,true);
        return list;
    }

    @RequestMapping(value = "/panel/update",method = RequestMethod.POST)
    public Result<Object> updatePanel(@ModelAttribute TbPanel tbPanel){
        panelService.updatePanel(tbPanel);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/panel/add",method = RequestMethod.POST)
    public Result<Object> addPanel(@ModelAttribute TbPanel tbPanel){
        panelService.addPanel(tbPanel);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/panel/del/{ids}",method = RequestMethod.DELETE)
    public Result<Object> deletePanel(@PathVariable int[] ids){
        for(int id : ids){
            panelService.deletePanel(id);
        }
        return new ResultUtil<Object>().setData(null);
    }

//    首页轮播图管理

    @RequestMapping(value = "/panel/indexBanner/list",method = RequestMethod.GET)
    public List<ZTreeNode> getIndexBannerPanel(){
        List<ZTreeNode> list = panelService.getPanelList(-1,true);
        return list;
    }

//    首页内容

    @RequestMapping(value = "/panel/index/list",method = RequestMethod.GET)
    public List<ZTreeNode> getPanelIndexList(){
        List<ZTreeNode> result =  panelService.getPanelList(0,false);
        return result;
    }
}
