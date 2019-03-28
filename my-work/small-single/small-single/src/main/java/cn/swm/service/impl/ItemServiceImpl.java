package cn.swm.service.impl;

import cn.swm.mapper.TbItemMapper;
import cn.swm.pojo.TbItem;
import cn.swm.pojo.TbItemExample;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    /**
     * 条件查询商品内容，并且分页
     * @param draw
     * @param start
     * @param length
     * @param cid
     * @param search
     * @param orderCol
     * @param orderDir
     * @return
     */
    @Override
    public DataTableResult getItemList(int draw, int start, int length, int cid, String search, String orderCol, String orderDir) {
        DataTableResult result = new DataTableResult();

        PageHelper.startPage(start/length+1,length);

        List<TbItem> list = tbItemMapper.selectItemByCondition(cid,"%"+search+"%",orderCol,orderDir);
        System.out.println("条件查询商品内容的时候返回的list:"+list.toString()+"--end");
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);

        result.setDraw(draw);
        //过滤出来的总条数
        result.setRecordsFiltered((int) pageInfo.getTotal());
        //数据的总条数
        result.setRecordsTotal(getAllItemCount().getRecordsTotal());
        result.setData(list);
        return result;
    }

    /**
     * 获得商品的总数
     * @return
     */
    @Override
    public DataTableResult getAllItemCount() {
        DataTableResult result = new DataTableResult();
        TbItemExample tbItemExample = new TbItemExample();
        long count = tbItemMapper.countByExample(tbItemExample);
        result.setRecordsTotal(Math.toIntExact(count));
        return result;
    }
}
