package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.mapper.TbItemCatMapper;
import cn.swm.pojo.TbItemCat;
import cn.swm.pojo.TbItemCatExample;
import cn.swm.pojo.common.Result;
import cn.swm.pojo.common.ZTreeNode;
import cn.swm.service.ItemCatService;
import cn.swm.utils.DtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    /**
     * 获得所有商品的分类信息
     * @return
     */
    @Override
    public List<ZTreeNode> getItemCatList(int parentId) {
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        //排序
        tbItemCatExample.setOrderByClause("sort_order");
        //条件查询，目的是不要一下子都显示出来了
        criteria.andParentIdEqualTo((long) parentId);
        List<TbItemCat> list = tbItemCatMapper.selectByExample(tbItemCatExample);
        List<ZTreeNode> result = new ArrayList<>();
        for(TbItemCat tbItemCat : list){
            ZTreeNode zTreeNode = DtoUtil.TbItemCat2ZTreeNode(tbItemCat);
            result.add(zTreeNode);
        }
        return result;
    }

    /**
     * 添加商品的分类
     * @param tbItemCat
     * @return
     */
    @Override
    public int addItemCat(TbItemCat tbItemCat) {

        tbItemCat.setCreated(new Date());
        tbItemCat.setUpdated(new Date());
        tbItemCat.setStatus(1);
        if(tbItemCatMapper.insert(tbItemCat)!=1){
            throw new SmallException("添加商品分类出错");
        }
        return 1;
    }

    /**
     * 根据id删除所选的分类,删除分类的时候，递归删除,
     * 根据id查找子节点是不是还有节点，如果有，就递归，如果没有，就删除当前的节点
     * @param id
     * @return
     */
    @Override
    public int deleteItemCat(int id) {
        deleteZtree(id);
        return 1;
    }

    /**
     * 根据表单提交的数据修改商品分类
     * @param tbItemCat
     * @return
     */
    @Override
    public int updateItemCat(TbItemCat tbItemCat) {
        TbItemCat old = tbItemCatMapper.selectByPrimaryKey(tbItemCat.getId());
        tbItemCat.setCreated(old.getCreated());
        tbItemCat.setUpdated(new Date());
        if(tbItemCatMapper.updateByPrimaryKey(tbItemCat)!=1){
            throw new SmallException("修改商品数据的时候出错");
        }
        return 1;
    }

    /**
     * 删除树的递归方法
     * @param id
     */
    private void deleteZtree(int id) {
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        criteria.andParentIdEqualTo((long) id);
        List<TbItemCat> list = tbItemCatMapper.selectByExample(tbItemCatExample);
        for(TbItemCat tbItemCat : list){
            deleteZtree(tbItemCat.getId().intValue());
        }
        if(tbItemCatMapper.deleteByPrimaryKey((long) id)!=1){
            throw new SmallException("删除树节点的时候出错");
        }
        return;
    }
}
