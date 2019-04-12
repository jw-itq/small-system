package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.common.jedis.JedisClient;
import cn.swm.mapper.TbItemDescMapper;
import cn.swm.mapper.TbItemMapper;
import cn.swm.mapper.TbPanelContentMapper;
import cn.swm.mapper.TbPanelMapper;
import cn.swm.pojo.*;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.front.AllGoodsResult;
import cn.swm.pojo.front.Product;
import cn.swm.pojo.front.ProductDet;
import cn.swm.service.ContentService;
import cn.swm.utils.DtoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    private final static Logger log = LoggerFactory.getLogger(PanelServiceImpl.class);

    @Autowired
    private TbPanelContentMapper tbPanelContentMapper;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbPanelMapper tbPanelMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Value("${HEADER_PANEL_ID}")
    private int HEADER_PANEL_ID;

    @Value("${HEADER_PANEL}")
    private String HEADER_PANEL;

    @Value("${PRODUCT_HOME}")
    private String PRODUCT_HOME;

    @Value("${PRODUCT_ITEM}")
    private String PRODUCT_ITEM;

    @Value("${ITEM_EXPIRE}")
    private Integer ITEM_EXPIRE;

    @Value("${RECOMEED_PANEL}")
    private String RECOMEED_PANEL;

    @Value("${RECOMEED_PANEL_ID}")
    private int RECOMEED_PANEL_ID;

    /**
     * 根据分类板块的id来获得分类表中的信息
     * @param panelId
     * @return
     */
    @Override
    public DataTableResult getPanelContentListByPanelId(int panelId) {
        DataTableResult result = new DataTableResult();
        List<TbPanelContent> list = new ArrayList<>();
        TbPanelContentExample tbPanelContentExample = new TbPanelContentExample();
        TbPanelContentExample.Criteria criteria = tbPanelContentExample.createCriteria();
        criteria.andPanelIdEqualTo(panelId);
        list = tbPanelContentMapper.selectByExample(tbPanelContentExample);
        for(TbPanelContent content : list){
            if(content.getProductId() != null){
                TbItem tbItem = tbItemMapper.selectByPrimaryKey(content.getProductId());
                content.setProductName(tbItem.getTitle());
                content.setSalePrice(tbItem.getPrice());
                content.setSubTitle(tbItem.getSellPoint());
            }
        }

        result.setData(list);
        return result;
    }

    /**
     * 添加首页导航栏的分类信息，在这里添加的时候要记得同步缓存，要记住，
     * 同步缓存的思路就是删除对应的缓存，因为只要删除了对应的缓存，要用到
     * 缓存的地方就会发现缓存中没有了对应的信息，然后就不会区缓存中区找，直接就会区查找数据库，就实现了同步
     * @param tbPanelContent
     * @return
     */
    @Override
    public int addContent(TbPanelContent tbPanelContent) {
        tbPanelContent.setCreated(new Date());
        tbPanelContent.setUpdated(new Date());
        if(tbPanelContentMapper.insert(tbPanelContent)!=1){
            throw new SmallException("添加首页板块内容失败");
        }
        //同步导航栏的缓存,前面的表单里面设置了隐藏域,PanelId=0
        if(tbPanelContent.getPanelId()==HEADER_PANEL_ID){
            updateNavListRedis();
        }

        //同步首页的缓存
        deleteHomeRedis();
        return 1;
    }

    /**
     * 根据id删除内容分类中的信息
     * @param id
     * @return
     */
    @Override
    public int deletePanelContent(int id) {
        if(tbPanelContentMapper.deleteByPrimaryKey(id)!=1){
            throw new SmallException("删除首页板块失败");
        }
        //同步导航栏的缓存
        if(id==HEADER_PANEL_ID){
            updateNavListRedis();
        }
        //同步首页的缓存
        deleteHomeRedis();
        return 1;
    }

    /**
     * 更新导航栏的内容，同时也要更新缓存,
     * 在这里更新的时候，要注意图片的更新，因为在前台如果用户不选择图片的话，就应该还是要使用原来的图片，
     * 所以说，这里就要查看到原来的TbPanelContent这个类的内容,
     *
     * 还有一点就是要说一下，保存图片的时候，要根据用户选择了多少张就保存多少张，并且更新以前的
     * @param tbPanelContent
     * @return
     */
    @Override
    public int updatePanelContent(TbPanelContent tbPanelContent) {
        TbPanelContent old = getTbPanelContentById(tbPanelContent.getId());
        //如果是空
        if(StringUtils.isBlank(tbPanelContent.getPicUrl())){
            tbPanelContent.setPicUrl(old.getPicUrl());
        }
        if(StringUtils.isBlank(tbPanelContent.getPicUrl2())){
            tbPanelContent.setPicUrl2(old.getPicUrl2());
        }
        if(StringUtils.isBlank(tbPanelContent.getPicUrl3())){
            tbPanelContent.setPicUrl3(old.getPicUrl3());
        }
        tbPanelContent.setCreated(old.getCreated());
        tbPanelContent.setUpdated(new Date());

        if(tbPanelContentMapper.updateByPrimaryKey(tbPanelContent)!=1){
            throw new SmallException("更新板块内容失败");
        }
        //同步导航栏的缓存
        if(tbPanelContent.getPanelId()==HEADER_PANEL_ID){
            updateNavListRedis();
        }
        //同步首页的缓存
        deleteHomeRedis();
        return 1;
    }

    /**
     * 获得首页的缓存，首页缓存的值是-PRODUCT_HOME
     * @return
     */
    @Override
    public String getIndexRedis() {
        try {
            String result = jedisClient.get(PRODUCT_HOME);
            return result;
        }catch (Exception e){
            log.error(e.toString());
        }
        return "";
    }

    /**
     * 刷新首页的缓存
     * @return
     */
    @Override
    public int updateIndexRedis() {
        deleteHomeRedis();
        return 1;
    }

    /**
     * 这里基本上要向前台返回所有的信息，
     * 首先是查询首页的板块信息，---》板块关联着板块的内容-----》板块的内容有关联着商品
     * 所以在返回所有的板块信息的时候，要附带着返回每个板块关联的所有板块内容信息，在内容信息里面包含了关联的商品信息
     * @return
     */
    @Override
    public List<TbPanel> getHome() {
        List<TbPanel> list = new ArrayList<>();

        //先查询缓存中是否存在，若是缓存中不存在就取数据库里面查询了添加到缓存中，下次直接从缓存中拿就行了
        try {
            String json = jedisClient.get(PRODUCT_HOME);
            if(json!=null){
                list = new Gson().fromJson(json, new TypeToken<List<TbPanel>>(){}.getType());
                log.info("读取了首页的缓存");
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //没有缓存就要取查询数据库
        TbPanelExample tbPanelExample = new TbPanelExample();
        TbPanelExample.Criteria criteria = tbPanelExample.createCriteria();
        //条件查询
        criteria.andPositionEqualTo(0);
        criteria.andStatusEqualTo(1);
        tbPanelExample.setOrderByClause("sort_order");
        list = tbPanelMapper.selectByExample(tbPanelExample);

        //添加tbpanel pojo里面的content***属性
        for(TbPanel tbPanel : list){
            TbPanelContentExample tbPanelContentExample = new TbPanelContentExample();
            TbPanelContentExample.Criteria criteria1 = tbPanelContentExample.createCriteria();
            tbPanelContentExample.setOrderByClause("sort_order");
            criteria1.andPanelIdEqualTo(tbPanel.getId());
            List<TbPanelContent> contentList = tbPanelContentMapper.selectByExample(tbPanelContentExample);

            //添加tbpanelContent里面的商品属性
            for(TbPanelContent tbPanelContent : contentList){
                if(tbPanelContent.getProductId()!=null){
                    TbItem tbItem = tbItemMapper.selectByPrimaryKey(tbPanelContent.getProductId());
                    tbPanelContent.setSalePrice(tbItem.getPrice());
                    tbPanelContent.setProductName(tbItem.getTitle());
                    tbPanelContent.setSubTitle(tbItem.getSellPoint());
                    tbPanelContent.setProductImageBig(tbPanelContent.getPicUrl());
                    //这里为什么只需要设置一下，是因为，我最后只是要拿到contentlist这个list对象，对象没有改变
                }
            }

            //添加到tbpanel对象里面去
            tbPanel.setPanelContents(contentList);
        }

        //将结果存到缓存中去
        try {
            jedisClient.set(PRODUCT_HOME,new Gson().toJson(list));
            log.info("添加了首页的缓存");
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 根据商品的id查询商品的详情
     * 1,首先去缓存中去拿
     * 2，如果缓存中没有，就去数据库中去查询，，，其中需要把商品详细表中的html也查询到
     * 3,最后记得重新添加到缓存中，并设置过期时间
     *
     * @param productId
     * @return
     */
    @Override
    public ProductDet getProductDet(Long productId) {

        try {
            //去缓存中去查询
            String json = jedisClient.get(PRODUCT_ITEM+":"+productId);
            if(json!=null){
                ProductDet productDet = new Gson().fromJson(json,ProductDet.class);
                log.info("读取了商品"+productId+"商品商品详情");
                //每次查询到了缓存，一定要记得重置redis的缓存
                jedisClient.expire(PRODUCT_ITEM+":"+productId,ITEM_EXPIRE);

                return productDet;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //没有缓存就要去数据库中去查询
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(productId);
        ProductDet productDet = new ProductDet();

        productDet.setProductName(tbItem.getTitle());
        productDet.setProductId(tbItem.getId());
        productDet.setSubTitle(tbItem.getSellPoint());

        //一定要保证limitNum里面有值，所以如果存在Tbitem中的limitnum没有值的话，那就取num的值，很明显，作者这个是为了做库存
        if(tbItem.getLimitNum()!=null&&!tbItem.getLimitNum().toString().isEmpty()){
            productDet.setLimitNum(Long.valueOf(tbItem.getLimitNum()));
        }else {
            productDet.setLimitNum(Long.valueOf(tbItem.getNum()));
        }

        productDet.setSalePrice(tbItem.getPrice());

        //插入商品详情html
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(productId);
        productDet.setDetail(tbItemDesc.getItemDesc());

        //插入商品的图片
        if(tbItem.getImage()!=null&&!tbItem.getImage().isEmpty()){
            String[] images = tbItem.getImage().split(",");
            productDet.setProductImageBig(images[0]);

            List<String> list = new ArrayList<>();
            for(String image : images){
                list.add(image);
            }

            productDet.setProductImageSmall(list);
        }

        try {
            //添加到缓存中
            jedisClient.set(PRODUCT_ITEM+":"+productId,new Gson().toJson(productDet));
            //设置缓存的过期时间
            jedisClient.expire(PRODUCT_ITEM+":"+productId,ITEM_EXPIRE);
            log.info("添加了商品"+productId+"详情缓存");
        }catch (Exception e){
            e.printStackTrace();
        }

        return productDet;
    }

    /**
     * 条件所有的商品信息
     * @param page 当前的页数
     * @param size 每页显示的大小
     * @param sort 排序值
     * @param cid
     * @param priceGt 最小价格
     * @param priceLte 最大价格
     * @return
     */
    @Override
    public AllGoodsResult getAllProduct(int page, int size, String sort, Long cid, int priceGt, int priceLte) {

        AllGoodsResult allGoodsResult = new AllGoodsResult();
        List<Product> list = new ArrayList<>();

        //分页查询
        if(page<=0){
            page = 1;
        }
        PageHelper.startPage(page,size);

        //判断条件，也就是前面传过来的排序的状态，，1，价格升序  -1，价格降序 否则就是按照创建时间来进行排序
        String orderCol = "created";
        String orderDir = "desc";
        if(sort.equals("1")){
            orderCol = "price";
            orderDir = "asc";
        }else if(sort.equals("-1")){
            orderCol = "price";
            orderDir = "desc";
        }else {
            orderCol = "created";
            orderDir = "desc";
        }

        /*

        List<TbItem> selectItemFront(@Param("cid") Long cid,
                                 @Param("orderCol") String orderCol, @Param("orderDir") String orderDir,
                                 @Param("priceGt") int priceGt, @Param("priceLte") int priceLte);
         */
        List<TbItem> tbItemList = tbItemMapper.selectItemFront(cid,orderCol,orderDir,priceGt,priceLte);
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItemList);

        for(TbItem tbItem : tbItemList){
            Product product = DtoUtil.TbItem2Product(tbItem);
            list.add(product);
        }

        allGoodsResult.setData(list);
        //得到总的数量
        allGoodsResult.setTotal((int) pageInfo.getTotal());

        return allGoodsResult;
    }

    /**
     * 得到分类板块中那个推荐板块的信息的信息
     * @return
     */
    @Override
    public List<TbPanel> getRecommendGoods() {

        List<TbPanel> list = new ArrayList<>();

        //先查询缓存
        try {
            String json = jedisClient.get(RECOMEED_PANEL);
            if(json!=null){
                list = new Gson().fromJson(json,new TypeToken<List<TbItem>>(){}.getType());
                log.info("读取了推荐板块的缓存");
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //再去数据库里面去查询，然后放入到redis缓存里面
        list = getTbpanelAndContenById(RECOMEED_PANEL_ID);

        //将结果存到缓存中去
        try {
            jedisClient.set(RECOMEED_PANEL,new Gson().toJson(list));
            log.info("添加了推荐板块的缓存");
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据id查询分类板块中的对应的分类板块的信息
     * @return
     */
    private List<TbPanel> getTbpanelAndContenById(int panelId){

        List<TbPanel> list = new ArrayList<>();

        TbPanelExample tbPanelExample = new TbPanelExample();
        TbPanelExample.Criteria criteria = tbPanelExample.createCriteria();
        criteria.andIdEqualTo(panelId);
        criteria.andStatusEqualTo(1);
        list = tbPanelMapper.selectByExample(tbPanelExample);

        if(list==null){
            return null;
        }
        TbPanel tbPanel = list.get(0);

        //添加tbpanel pojo里面的content***属性
        TbPanelContentExample tbPanelContentExample = new TbPanelContentExample();
        TbPanelContentExample.Criteria criteria1 = tbPanelContentExample.createCriteria();
        tbPanelContentExample.setOrderByClause("sort_order");
        criteria1.andPanelIdEqualTo(tbPanel.getId());
        List<TbPanelContent> contentList = tbPanelContentMapper.selectByExample(tbPanelContentExample);

        //添加tbpanelContent里面的商品属性
        for(TbPanelContent tbPanelContent : contentList){
            if(tbPanelContent.getProductId()!=null){
                TbItem tbItem = tbItemMapper.selectByPrimaryKey(tbPanelContent.getProductId());
                tbPanelContent.setSalePrice(tbItem.getPrice());
                tbPanelContent.setProductName(tbItem.getTitle());
                tbPanelContent.setSubTitle(tbItem.getSellPoint());
                //这里为什么只需要设置一下，是因为，我最后只是要拿到contentlist这个list对象，对象没有改变
            }
        }

        //添加到tbpanel对象里面去
        tbPanel.setPanelContents(contentList);

        list.add(tbPanel);
        return list;
    }

    /**
     * 根据id查询内容分类表的信息，这里主要是为了配合上面的更新来使用的
     * @param id
     * @return
     */
    private TbPanelContent getTbPanelContentById(Integer id) {
        TbPanelContent tbPanelContent = tbPanelContentMapper.selectByPrimaryKey(id);
        if(tbPanelContent==null){
            throw new SmallException("根据id获得板块内容失败");
        }
        return tbPanelContent;
    }

    /**
     * 同步首页的缓存
     */
    private void deleteHomeRedis() {
        try {
            jedisClient.del(PRODUCT_HOME);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 同步到导航栏的缓存
     */
    private void updateNavListRedis() {
        try {
            jedisClient.del(HEADER_PANEL);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
