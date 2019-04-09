package cn.swm.service;

import cn.swm.pojo.TbItemCat;
import cn.swm.pojo.common.Result;
import cn.swm.pojo.common.ZTreeNode;

import java.util.List;

public interface ItemCatService {

    List<ZTreeNode> getItemCatList(int parentId);

    int addItemCat(TbItemCat tbItemCat);

    int deleteItemCat(int id);

    int updateItemCat(TbItemCat tbItemCat);
}
