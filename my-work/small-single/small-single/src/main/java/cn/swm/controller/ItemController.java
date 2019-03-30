package cn.swm.controller;

import cn.swm.mapper.TbPanelContentMapper;
import cn.swm.mapper.TbPanelMapper;
import cn.swm.pojo.TbItem;
import cn.swm.pojo.TbPanelContent;
import cn.swm.pojo.TbPanelContentExample;
import cn.swm.pojo.TbPanelExample;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.common.Result;
import cn.swm.pojo.common.ZTreeNode;
import cn.swm.pojo.dto.EsInfo;
import cn.swm.pojo.dto.ItemDto;
import cn.swm.service.ItemService;
import cn.swm.service.SearchItemService;
import cn.swm.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private TbPanelContentMapper tbPanelContentMapper;

    @Autowired
    private SearchItemService searchItemService;


    /**
     * 前台的$('.table').DataTable({会自动的携带一系列的参数
     * 前端传递参数：
     * columns[0][data]    0
     * columns[0][name]
     * columns[0][orderable]    true
     * columns[0][search][regex]    false
     * columns[0][search][value]
     * columns[0][searchable]    true
     * columns[1][data]    1
     * columns[1][name]
     * columns[1][orderable]    true
     * columns[1][search][regex]    false
     * columns[1][search][value]
     * columns[1][searchable]    true
     * columns[2][data]    2
     * columns[2][name]
     * columns[2][orderable]    true
     * columns[2][search][regex]    false
     * columns[2][search][value]
     * columns[2][searchable]    true
     * columns[3][data]    3 // data[3]
     * columns[3][name]
     * columns[3][orderable]    true         //可以排序
     * columns[3][search][regex]    false         //搜索内容不支持正则表达式
     * columns[3][search][value]    //搜索的内容
     * columns[3][searchable]    true  //可以被搜索
     * draw    1    //浏览器cache的编号，递增不可重复
     * length    10 //预读长度= 预读页数*每页行数
     * order[0][column]    0  //按第一列排序
     * order[0][dir]    asc
     * search[regex]    false //搜索内容不支持正则表达式
     * search[value]    //输入的搜索的内容
     * start    0//起始位子，如第一页就从
     *
     * 返回值格式：
     * {
     *   "draw": 10, //浏览器cache的编号，递增不可重复
     *   "recordsTotal": 57, //数据总行数
     *   "recordsFiltered": 57, //过滤出来的数据总行数
     *   "data": [
     *     [
     *       "Cara",
     *       "Stevens",
     *       "Sales Assistant",
     *       "New York",
     *       "6th Dec 11",
     *       "$145,600"
     *     ],
     *     [
     *       "Shou",
     *       "Itou",
     *       "Regional Marketing",
     *       "Tokyo",
     *       "14th Aug 11",
     *       "$163,000"
     *     ]
     *   ]
     * }
     *
     * ---------------------
     * 作者：倪舒扬
     * 来源：CSDN
     * 原文：https://blog.csdn.net/u010403387/article/details/46355533
     * 版权声明：本文为博主原创文章，转载请附上博文链接！
     * @return
     */
    @RequestMapping(value = "/item/list",method = RequestMethod.GET)
    public DataTableResult getItemList(int draw, int start, int length, int cid, @RequestParam("search[value]") String search,
                                       @RequestParam("order[0][column]") int orderCol, @RequestParam("order[0][dir]") String orderDir,
                                       String searchItem, String minDate, String maxDate){
        //获取客户端需要排序的列
        String[] cols = {"checkbox","id", "image", "title", "sell_point", "price", "created", "updated", "status"};
        String orderColumn = cols[orderCol];
        if(orderColumn == null) {
            orderColumn = "created";
        }
        //获取排序方式 默认为desc(asc)
        if(orderDir == null) {
            orderDir = "desc";
        }
        DataTableResult result = itemService.getItemList(draw,start,length,cid,search,orderColumn,orderDir);
        return result;
    }

    @RequestMapping(value = "/item/count",method = RequestMethod.GET)
    public DataTableResult getItemCount(){
        return itemService.getAllItemCount();
    }

    @RequestMapping(value = "/item/stop/{id}",method = RequestMethod.PUT)
    public Result<Object> stopItem(@PathVariable("id")long id){
        itemService.alertItemState(id,0);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/item/start/{id}",method = RequestMethod.PUT)
    public Result<Object> startItem(@PathVariable("id")long id){
        itemService.alertItemState(id,1);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/item/{id}",method = RequestMethod.GET)
    public Result<ItemDto> getItemById(@PathVariable("id")long id){
        ItemDto itemDto = itemService.getItemById(id);
        return new ResultUtil<ItemDto>().setData(itemDto);
    }

    @RequestMapping(value = "/item/update/{id}",method = RequestMethod.POST)
    public Result<TbItem> updateItem(@PathVariable("id")long id,ItemDto itemDto){
        TbItem tbItem = itemService.updateItem(id,itemDto);
        return new ResultUtil<TbItem>().setData(tbItem);
    }

    @RequestMapping(value = "/item/del/{ids}",method = RequestMethod.DELETE)
    public Result<Object> deleteItemById(@PathVariable("ids")long[] ids){

        //判断首页是否关联，如果首页关联了商品，就不能在这里删除，要先把首页的配置信息删除
        for(long id : ids){
            TbPanelContentExample tbPanelContentExample = new TbPanelContentExample();
            TbPanelContentExample.Criteria criteria = tbPanelContentExample.createCriteria();
            criteria.andProductIdEqualTo(id);
            List<TbPanelContent> list = tbPanelContentMapper.selectByExample(tbPanelContentExample);
            if(list!=null&&list.size()>0){
                return new ResultUtil<Object>().setErrorMsg("删除失败！这个商品是首页展示中关联的商品，请先从首页配置中删除关联");
            }
        }

        for(long id : ids){
            itemService.deleteItemById(id);
        }
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/item/add",method = RequestMethod.POST)
    public Result<Object> addItem(ItemDto itemDto){
        itemService.addItem(itemDto);
        return new ResultUtil<Object>().setData(null);
    }

    /**
     * 多条件分页查询
     * var param = {
     *                 "searchKey": searchKey,
     *                 "minDate": minDate,
     *                 "maxDate":maxDate,
     *                 "cid":cid
     *             };
     */

    /**
     *
     * @param draw 浏览器cache的编号，递增不能重复
     * @param start 其实位置
     * @param length 预读长度=预读的页数*每页的条数
     * @param cid 根据类别来查询，前台初始化为-1
     * @param search 表格查询的条件
     * @param orderCol 排序的字段
     * @param orderDir 排序的方式
     * @param searchKey 用户输入的查询条件
     * @param minDate 查询最小的日期
     * @param maxDate 查询最大的日期
     * @return
     */
    @RequestMapping(value = "/item/listSearch",method = RequestMethod.GET)
    public DataTableResult getSearchItemList(int draw, int start, int length, int cid, @RequestParam("search[value]") String search,
                                             @RequestParam("order[0][column]") int orderCol, @RequestParam("order[0][dir]") String orderDir,
                                             String searchKey,String minDate,String maxDate){
        //获取客户端需要排序的列
        String[] cols = {"checkbox","id", "image", "title", "sell_point", "price", "created", "updated", "status"};
        String orderColume = cols[orderCol];
        if(orderColume==null){
            orderColume = "created";
        }

        if(orderDir==null){
            orderDir = "desc";
        }

        if(!search.isEmpty()){
            searchKey = search;
        }
        return itemService.getSearchItemList(draw,start,length,cid,orderColume,orderDir,searchKey,minDate,maxDate);
    }


    //获得索引信息
    @RequestMapping(value = "/es/getInfo",method = RequestMethod.GET)
    public Result<Object> getESInfo(){
        EsInfo esInfo = searchItemService.getESInfo();
        return new ResultUtil<Object>().setData(esInfo);
    }

    //同步索引库
    @RequestMapping(value = "/item/importIndex",method = RequestMethod.GET)
    public Result<Object> importIndex(){
        searchItemService.importIndex();
        return new ResultUtil<Object>().setData(null);
    }

}
