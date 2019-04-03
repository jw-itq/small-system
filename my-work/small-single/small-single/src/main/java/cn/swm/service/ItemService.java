package cn.swm.service;

import cn.swm.pojo.TbItem;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.common.Result;
import cn.swm.pojo.common.ZTreeNode;
import cn.swm.pojo.dto.ItemDto;
import org.springframework.web.bind.annotation.RequestParam;

public interface ItemService {

    DataTableResult getItemList(int draw, int start, int length, int cid, String search,
                                String orderCol, String orderDir);

    /**
     * 获得商品的总数
     * @return
     */
    DataTableResult getAllItemCount();

    int alertItemState(long id,Integer state);

    ItemDto getItemById(long id);

    TbItem updateItem(long id,ItemDto itemDto);

    int deleteItemById(long id);

    int addItem(ItemDto itemDto);

    DataTableResult getSearchItemList(int draw, int start, int length, int cid,
                                      String orderColume, String orderDir,
                                      String searchKey,String minDate,String maxDate);
}
