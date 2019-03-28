package cn.swm.controller;

import cn.swm.pojo.common.DataTableResult;
import cn.swm.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;


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
}
