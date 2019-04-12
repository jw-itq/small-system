package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.mapper.TbItemMapper;
import cn.swm.pojo.TbItem;
import cn.swm.pojo.dto.EsCount;
import cn.swm.pojo.dto.EsInfo;
import cn.swm.pojo.front.SearchItem;
import cn.swm.service.SearchItemService;
import cn.swm.utils.HttpUtil;
import com.google.gson.Gson;
import org.apache.commons.collections.IterableMap;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.network.InetAddresses;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Service
public class SearchItemServiceImpl implements SearchItemService {

    private final static Logger log = LoggerFactory.getLogger(SearchItemServiceImpl.class);

    @Autowired
    private TbItemMapper tbItemMapper;

    @Value("${ES_CLUSTER_NAME}")
    private String ES_CLUSTER_NAME;

    @Value("${ES_CONNECT_IP}")
    private String ES_CONNECT_IP;

    @Value("${ITEM_INDEX}")
    private String ITEM_INDEX;

    @Value("${ITEM_TYPE}")
    private String ITEM_TYPE;

    @Value("${ES_NODE_CLIENT_PORT}")
    private String ES_NODE_CLIENT_PORT;


    /**
     * 更新商品信息的索引
     * @param type 表示类型，0：更新,1：删除
     * @param itemId 商品id
     * @return
     */
    @Override
    public int refreshItem(int type, long itemId) {
        try {
            //更新索引
            Settings settings = Settings.builder().put("cluster.name",ES_CLUSTER_NAME).build();
            TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName(ES_CONNECT_IP),9300));

            if(type==0){
                //根据商品id查询商品的信息
                SearchItem item = tbItemMapper.getItemById(itemId);
                if(item==null){
                    log.error("根据id查询商品信息的时候，没查到，多半是因为status是0，也就不需要同步索引");
                    return 0;
                }

                String image = item.getProductImageBig();
                if(image!=null&&!"".equals(image)){
                    String[] strings = image.split(",");
                    image = strings[0];
                }else {
                    image = "";
                }

                item.setProductImageBig(image);
                IndexResponse indexResponse = client.prepareIndex(ITEM_INDEX,ITEM_TYPE,String.valueOf(item.getProductId()))
                        .setSource(jsonBuilder()
                                .startObject()
                                .field("productId", item.getProductId())
                                .field("salePrice", item.getSalePrice())
                                .field("productName", item.getProductName())
                                .field("subTitle", item.getSubTitle())
                                .field("productImageBig", item.getProductImageBig())
                                .field("categoryName", item.getCategoryName())
                                .field("cid", item.getCid())
                                .endObject()
                        ).get();

            }else {
                DeleteResponse deleteResponse = client.prepareDelete(ITEM_INDEX,ITEM_TYPE,String.valueOf(itemId)).get();
            }

            log.info("同步商品："+itemId+"索引成功");

            client.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return 1;
    }

    /**
     * 获得索引的信息
     * @return
     */
    @Override
    public EsInfo getESInfo() {
        String healthUrl = "http://"+ES_CONNECT_IP+":"+ES_NODE_CLIENT_PORT+"/_cluster/health";
        String countUrl = "http://"+ES_CONNECT_IP+":"+ES_NODE_CLIENT_PORT+"/_count";
        String healthResult = HttpUtil.sendGet(healthUrl);
        String countResult = HttpUtil.sendGet(countUrl);
        if(StringUtils.isBlank(healthResult)||StringUtils.isBlank(countResult)){
            throw new SmallException("连接集群失败，请检查ES运行状态");
        }
        EsInfo esInfo = new EsInfo();
        EsCount esCount = new EsCount();
        try {
            esInfo = new Gson().fromJson(healthResult,EsInfo.class);
            esCount = new Gson().fromJson(countResult,EsCount.class);
            esInfo.setCount(esCount.getCount());
        }catch (Exception e){
            e.printStackTrace();
            throw new SmallException("获取ES信息出错");
        }
        return esInfo;
    }

    /**
     * 同步索引库
     * @return
     */
    @Override
    public int importIndex() {
        try {
            Settings settings = Settings.builder().put("cluster.name",ES_CLUSTER_NAME).build();
            TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(ES_CONNECT_IP),9300));

            //批量添加
            BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

            //查询商品列表
            List<SearchItem> itemlist = tbItemMapper.getItemList();

            log.info("获取商品信息成功:"+itemlist.size());

            //遍历商品列表
            for(SearchItem item : itemlist){
                String image = item.getProductImageBig();
                if(image!=null && !"".equals(image)){
                    String[] strings = image.split(",");
                    image = strings[0];
                }else {
                    image = "";
                }
                item.setProductImageBig(image);
                bulkRequestBuilder.add(client.prepareIndex(ITEM_INDEX, ITEM_TYPE, String.valueOf(item.getProductId()))
                        .setSource(jsonBuilder()
                                .startObject()
                                .field("productId", item.getProductId())
                                .field("salePrice", item.getSalePrice())
                                .field("productName", item.getProductName())
                                .field("subTitle", item.getSubTitle())
                                .field("productImageBig", item.getProductImageBig())
                                .field("categoryName", item.getCategoryName())
                                .field("cid", item.getCid())
                                .endObject()
                        )
                );
            }

            /**
             * 这里本来是添加索引的映射的，但是我也不知道到底有没有用，再还没弄明白之前在这里些个注释，提醒下，，，，目的就是之前排序的时候出问题了
             *
             * 天啊，这段代码有着关键性的作用阿，啊啊啊啊
             */
            client.admin().indices().prepareCreate("item")
                    .addMapping("itemList", "salePrice", "type=integer")
                    .get();

            BulkResponse bulkItemResponses = bulkRequestBuilder.get();

            log.info("更新索引信息成功");

            client.close();
        }catch (Exception e){
            e.printStackTrace();
            throw new SmallException("导入ES索引库的时候出错");
        }
        return 1;
    }

    /**
     * 批量删除所有的索引
     * @return
     */
    @Override
    public int deleteAllIndex() {
        try {
            Settings settings = Settings.builder().put("cluster.name",ES_CLUSTER_NAME).build();
            TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(ES_CONNECT_IP),9300));

            //查询商品列表
            List<SearchItem> itemlist = tbItemMapper.getItemList();

            log.info("获取商品信息成功:"+itemlist.size());

            //遍历商品列表，根据id来删除
            /*for(SearchItem item : itemlist){
                DeleteResponse deleteResponse = client.prepareDelete(ITEM_INDEX,ITEM_TYPE,String.valueOf(item.getProductId())).get();
            }*/
            DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(ITEM_INDEX)
                    .execute().actionGet();

            log.info("删除索引信息成功");

            client.close();
        }catch (Exception e){
            e.printStackTrace();
            throw new SmallException("删除ES索引库的时候出错");
        }
        return 0;
    }
}
