package cn.swm.service;

import cn.swm.pojo.TbPanel;
import cn.swm.pojo.TbPanelContent;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.front.AllGoodsResult;
import cn.swm.pojo.front.ProductDet;

import java.util.List;

public interface ContentService {

    DataTableResult getPanelContentListByPanelId(int panelId);

    int addContent(TbPanelContent tbPanelContent);

    int deletePanelContent(int id);

    int updatePanelContent(TbPanelContent tbPanelContent);

    String getIndexRedis();

    int updateIndexRedis();

    List<TbPanel> getHome();

    ProductDet getProductDet(Long productId);

    AllGoodsResult getAllProduct(int page,int size,String sort,Long cid,int priceGt,int priceLte);

    List<TbPanel> getRecommendGoods();

}
