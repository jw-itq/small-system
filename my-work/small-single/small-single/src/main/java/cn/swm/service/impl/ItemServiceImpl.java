package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.common.jedis.JedisClient;
import cn.swm.mapper.TbItemCatMapper;
import cn.swm.mapper.TbItemDescMapper;
import cn.swm.mapper.TbItemMapper;
import cn.swm.pojo.*;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.common.Result;
import cn.swm.pojo.common.ZTreeNode;
import cn.swm.pojo.dto.ItemDto;
import cn.swm.service.ItemService;
import cn.swm.service.SearchItemService;
import cn.swm.utils.DtoUtil;
import cn.swm.utils.IDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final static Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private SearchItemService searchItemService;

    @Autowired
    private JedisClient jedisClient;

    @Value("${PRODUCT_ITEM}")
    private String PRODUCT_ITEM;

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

    /**
     * 根据商品的id修改商品的状态为0，其实就是下架
     * @param id
     * @return
     */
    @Override
    public int alertItemState(long id,Integer state) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        tbItem.setStatus(state);
        tbItem.setUpdated(new Date());
        if(tbItemMapper.updateByPrimaryKey(tbItem)!=1){
            throw new SmallException("修改商品状态的时候出错");
        }
        return 1;
    }

    /**
     * 根据id查询商品的详细信息
     * @param id
     * @return
     */
    @Override
    public ItemDto getItemById(long id) {
        ItemDto itemDto = new ItemDto();

        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        itemDto = DtoUtil.TbItem2ItemDto(tbItem);

        TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(tbItem.getCid());
        itemDto.setCname(tbItemCat.getName());

        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
        itemDto.setDetail(tbItemDesc.getItemDesc());

        return itemDto;
    }

    /**
     * 更新商品的信息，这里的id是为了得到原来的商品信息
     * @param id
     * @param itemDto
     * @return
     */
    @Override
    public TbItem updateItem(long id, ItemDto itemDto) {
        TbItem old = tbItemMapper.selectByPrimaryKey(id);

        TbItem tbItem = DtoUtil.ItemDto2TbItem(itemDto);
        if(tbItem.getImage().isEmpty()){
            tbItem.setImage(old.getImage());
        }
        tbItem.setId(id);
        tbItem.setStatus(old.getStatus());
        tbItem.setCreated(old.getCreated());
        tbItem.setUpdated(new Date());
        if(tbItemMapper.updateByPrimaryKey(tbItem)!=1){
            throw new SmallException("更新商品信息的时候出错");
        }

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setCreated(old.getCreated());
        tbItemDesc.setItemDesc(itemDto.getDetail());
        tbItemDesc.setItemId(id);
        tbItemDesc.setUpdated(new Date());
        if(tbItemDescMapper.updateByPrimaryKey(tbItemDesc)!=1){
            throw new SmallException("更新商品详细信息的时候出错");
        }

        //同步缓存
        deleteProductDetRedis(id);

        //发送消息同步到索引
        try {
            searchItemService.refreshItem(0,id);
        }catch (Exception e){
            log.error(e.toString());
        }

        return tbItemMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据id删除商品的信息,并且一起删除商品详细表的信息,
     * @param id
     * @return
     */
    @Override
    public int deleteItemById(long id) {

        if(tbItemMapper.deleteByPrimaryKey(id)!=1){
            throw new SmallException("删除商品的时候出错");
        }

        if(tbItemDescMapper.deleteByPrimaryKey(id)!=1){
            throw new SmallException("删除商品详情表的时候出错");
        }

        //同步索引库，也就是要删除索引库中对应的商品
        try {
            searchItemService.refreshItem(1,id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 添加商品的信息，添加商品的详细信息
     * @param itemDto
     * @return
     */
    @Override
    public int addItem(ItemDto itemDto) {

        long id = IDUtil.getRandomId();
        while (true){
            if(tbItemMapper.selectByPrimaryKey(id)!=null){
                id = IDUtil.getRandomId();
                continue;
            }
            break;
        }

        TbItem tbItem = DtoUtil.ItemDto2TbItem(itemDto);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        tbItem.setStatus(1);
        tbItem.setId(id);
        if(tbItem.getImage().isEmpty()){
            tbItem.setImage("http://pp2fbljdl.bkt.clouddn.com/cat.png");
        }
        if(tbItemMapper.insert(tbItem)!=1){
            throw new SmallException("添加商品信息出错");
        }

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setItemId(id);
        tbItemDesc.setItemDesc(itemDto.getDetail());
        if(tbItemDescMapper.insert(tbItemDesc)!=1){
            throw new SmallException("添加商品详细的时候出错");
        }

        //同步到索引库
        try {
            searchItemService.refreshItem(0,id);
        }catch (Exception e){
            log.error(e.toString());
        }

        return 1;
    }

    /**
     * 多条件查询商品信息，并分页
     * @param draw
     * @param start
     * @param length
     * @param cid
     * @param orderColume
     * @param orderDir
     * @param searchKey
     * @param minDate
     * @param maxDate
     * @return
     */
    @Override
    public DataTableResult getSearchItemList(int draw, int start, int length, int cid, String orderColume, String orderDir, String searchKey, String minDate, String maxDate) {
        DataTableResult result = new DataTableResult();
        PageHelper.startPage(start/length+1,length);
        List<TbItem> list = tbItemMapper.getSearchItemList(cid,orderColume,orderDir,"%"+searchKey+"%",minDate,maxDate);
        PageInfo pageInfo = new PageInfo(list);
        result.setDraw(draw);
        result.setRecordsFiltered((int) pageInfo.getTotal());
        result.setRecordsTotal(getAllItemCount().getRecordsTotal());
        result.setData(list);
        return result;
    }

    //同步商品详情缓存
    public void deleteProductDetRedis(long id){
        try {
            jedisClient.del("PRODUCT_ITEM"+":"+id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
