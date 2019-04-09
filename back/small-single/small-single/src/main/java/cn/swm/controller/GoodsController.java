package cn.swm.controller;

import cn.swm.pojo.TbPanel;
import cn.swm.pojo.common.Result;
import cn.swm.pojo.front.AllGoodsResult;
import cn.swm.pojo.front.ProductDet;
import cn.swm.pojo.front.SearchResult;
import cn.swm.service.ContentService;
import cn.swm.service.SearchService;
import cn.swm.utils.HttpUtil;
import cn.swm.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GoodsController {

    private final static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private ContentService contentService;

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/goods/home",method = RequestMethod.GET)
    public Result<List<TbPanel>> getProductHome(){
        List<TbPanel> list = contentService.getHome();
        return new ResultUtil<List<TbPanel>>().setData(list);
    }

    @RequestMapping(value = "/goods/productDet",method = RequestMethod.GET)
    public Result<ProductDet> getProductDet(Long productId){
        ProductDet productDet = contentService.getProductDet(productId);
        return new ResultUtil<ProductDet>().setData(productDet);
    }

    @RequestMapping(value = "/goods/allGoods",method = RequestMethod.GET)
    public Result<AllGoodsResult> getAllGoods(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "20") int size,
                                              @RequestParam(defaultValue = "") String sort,
                                              @RequestParam(defaultValue = "") Long cid,
                                              @RequestParam(defaultValue = "-1") int priceGt,
                                              @RequestParam(defaultValue = "-1") int priceLte){
        AllGoodsResult allGoodsResult = contentService.getAllProduct(page,size,sort,cid,priceGt,priceLte);
        return new ResultUtil<AllGoodsResult>().setData(allGoodsResult);
    }

    @RequestMapping(value = "/goods/recommend",method = RequestMethod.GET)
    public Result<List<TbPanel>> getRecommentGoods(){

        List<TbPanel> list = contentService.getRecommendGoods();
        return new ResultUtil<List<TbPanel>>().setData(list);
    }

    @RequestMapping(value = "/goods/search",method = RequestMethod.GET)
    public Result<SearchResult> searchProduct(@RequestParam(defaultValue = "") String key,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "20") int size,
                                              @RequestParam(defaultValue = "") String sort,
                                              @RequestParam(defaultValue = "-1") int priceGt,
                                              @RequestParam(defaultValue = "-1") int priceLte){
        SearchResult searchResult = searchService.searchProduct(key,size,page,sort,priceGt,priceLte);

        return new ResultUtil<SearchResult>().setData(searchResult);
    }

    /**
     * 这里的快速搜索是为了实现搜索输入框下面的那个提示
     * @param key
     * @return
     */
    @RequestMapping(value = "/goods/quickSearch",produces= "text/plain;charset=UTF-8",method = RequestMethod.GET)
    public String getQuickSearch(@RequestParam(defaultValue = "") String key){

        String result = null;
        try {
            result = HttpUtil.sendGet("http://localhost:9200/item/itemList/_search?q=productName:"+key);
        }catch (Exception e){
            log.error(e.toString());
        }
        return result;
    }


}
