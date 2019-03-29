package cn.swm.mapper;

import cn.swm.pojo.TbItem;
import cn.swm.pojo.TbItemExample;
import java.util.List;

import cn.swm.pojo.front.SearchItem;
import org.apache.ibatis.annotations.Param;

public interface TbItemMapper {
    long countByExample(TbItemExample example);

    int deleteByExample(TbItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbItem record);

    int insertSelective(TbItem record);

    List<TbItem> selectByExample(TbItemExample example);

    TbItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbItem record, @Param("example") TbItemExample example);

    int updateByExample(@Param("record") TbItem record, @Param("example") TbItemExample example);

    int updateByPrimaryKeySelective(TbItem record);

    int updateByPrimaryKey(TbItem record);

//-------
    List<TbItem> selectItemByCondition(@Param("cid") int cid, @Param("search") String search,
                                   @Param("orderCol") String orderCol, @Param("orderDir") String orderDir);

    SearchItem getItemById(@Param("id")long id);

    List<SearchItem> getItemList();

    List<TbItem> getSearchItemList(@Param("cid") int cid,@Param("orderCol") String orderColume,
                                   @Param("orderDir") String orderDir, @Param("searchKey") String searchKey,
                                   @Param("minDate") String minDate,@Param("maxDate") String maxDate);
}
