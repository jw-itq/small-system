package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.common.jedis.JedisClient;
import cn.swm.mapper.TbItemMapper;
import cn.swm.mapper.TbPanelContentMapper;
import cn.swm.pojo.TbItem;
import cn.swm.pojo.TbPanelContent;
import cn.swm.pojo.TbPanelContentExample;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.service.ContentService;
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

    @Value("${HEADER_PANEL_ID}")
    private int HEADER_PANEL_ID;

    @Value("${HEADER_PANEL}")
    private String HEADER_PANEL;

    @Value("${PRODUCT_HOME}")
    private String PRODUCT_HOME;

    /**
     * 根据分类的id来获得分类表中的信息
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
     * 所以说，这里就要查看到原来的TbPanelContent这个类的内容
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
