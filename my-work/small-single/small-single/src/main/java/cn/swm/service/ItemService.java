package cn.swm.service;

import cn.swm.pojo.common.DataTableResult;

public interface ItemService {

    DataTableResult getItemList(int draw, int start, int length, int cid, String search,
                                String orderCol, String orderDir);

    /**
     * 获得商品的总数
     * @return
     */
    DataTableResult getAllItemCount();
}
