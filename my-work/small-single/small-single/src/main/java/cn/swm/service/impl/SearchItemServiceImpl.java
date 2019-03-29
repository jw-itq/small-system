package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.mapper.TbItemMapper;
import cn.swm.pojo.front.SearchItem;
import cn.swm.service.SearchItemService;
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


    /**
     * 更新商品信息的索引
     * @param type 表示类型，0：更新,1：删除
     * @param itemId
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
                SearchItem searchItem = tbItemMapper.getItemById(itemId);
                String image = searchItem.getProductImageBig();
                if(image!=null&&!"".equals(image)){
                    String[] strings = image.split(",");
                    image = strings[0];
                }else {
                    image = "";
                }

                searchItem.setProductImageBig(image);
                IndexResponse indexResponse = client.prepareIndex(ITEM_INDEX,ITEM_TYPE,String.valueOf(searchItem.getProductId()))
                        .setSource(jsonBuilder()
                                .startObject()
                                .field("productId", searchItem.getProductId())
                                .field("salePrice", searchItem.getSalePrice())
                                .field("productName", searchItem.getProductName())
                                .field("subTitle", searchItem.getSubTitle())
                                .field("productImageBig", searchItem.getProductImageBig())
                                .field("categoryName", searchItem.getCategoryName())
                                .field("cid", searchItem.getCid())
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
}
