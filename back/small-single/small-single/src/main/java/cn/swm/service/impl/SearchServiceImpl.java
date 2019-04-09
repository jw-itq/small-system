package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.mapper.TbItemMapper;
import cn.swm.pojo.front.SearchItem;
import cn.swm.pojo.front.SearchResult;
import cn.swm.service.SearchService;
import com.google.gson.Gson;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {


    private final static Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);

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
     * 根据条件去es里面搜索产品,,,,在这里就没有去数据库里面去查询了，，，要记得提前导入
     * @param key 关键字
     * @param size 每页显示的数量
     * @param page 当前的页数
     * @param sort 排序的类型，，1，价格的升序，-1，价格的降序，否则不排序
     * @param priceGt 最小值
     * @param priceLte 最大值
     * @return 返回的是一个前台的front dto
     */
    @Override
    public SearchResult searchProduct(String key, int size, int page, String sort, int priceGt, int priceLte) {

        try{
            Settings settings = Settings.builder()
                    .put("cluster.name", ES_CLUSTER_NAME).build();
            TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(ES_CONNECT_IP), 9300));

            SearchResult searchResult=new SearchResult();

            //设置查询条件
            //QueryBuilder qb = matchQuery("categoryName",key);

//			设置多个字段
//			//第一个参数是查询的值，后面的参数是字段名，可以跟多个字段，用逗号隔开
//        QueryBuilder builder = QueryBuilders.multiMatchQuery("changge", "address","interests");
            //我这里为什么要设置多条件，是因为我首页的分类导航，就需要到这里来查询
            QueryBuilder qb = QueryBuilders.multiMatchQuery(key,"productName","categoryName","subTitle");


            //设置分页
            if (page <=0 ){
                page =1;
            }
            int start=(page - 1) * size;

            //设置高亮显示
            HighlightBuilder hiBuilder=new HighlightBuilder();
            hiBuilder.preTags("<a style=\"color: #e4393c\">");
            hiBuilder.postTags("</a>");
            hiBuilder.field("productName").field("subTitle");

            //执行搜索
            SearchResponse searchResponse = null;

            if(priceGt>=0&&priceLte>=0&&sort.isEmpty()){
                searchResponse=client.prepareSearch("item")
                        .setTypes("itemList")
                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                        .setQuery(qb)	// Query
                        .setFrom(start).setSize(size).setExplain(true)	//从第几个开始，显示size个数据
                        .highlighter(hiBuilder)		//设置高亮显示
                        .setPostFilter(QueryBuilders.rangeQuery("salePrice").gt(priceGt).lt(priceLte))	//过滤条件
                        .get();
            }else if(priceGt>=0&&priceLte>=0&&sort.equals("1")){
                searchResponse=client.prepareSearch("item")
                        .setTypes("itemList")
                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                        .setQuery(qb)	// Query
                        .setFrom(start).setSize(size).setExplain(true)	//从第几个开始，显示size个数据
                        .highlighter(hiBuilder)		//设置高亮显示
                        .setPostFilter(QueryBuilders.rangeQuery("salePrice").gt(priceGt).lt(priceLte))	//过滤条件
                        .addSort("salePrice", SortOrder.ASC)
                        .get();
            }else if(priceGt>=0&&priceLte>=0&&sort.equals("-1")){
                searchResponse=client.prepareSearch("item")
                        .setTypes("itemList")
                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                        .setQuery(qb)	// Query
                        .setFrom(start).setSize(size).setExplain(true)	//从第几个开始，显示size个数据
                        .highlighter(hiBuilder)		//设置高亮显示
                        .setPostFilter(QueryBuilders.rangeQuery("salePrice").gt(priceGt).lt(priceLte))	//过滤条件
                        .addSort("salePrice", SortOrder.DESC)
                        .get();
            }else if((priceGt<0||priceLte<0)&&sort.isEmpty()){
                searchResponse=client.prepareSearch("item")
                        .setTypes("itemList")
                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                        .setQuery(qb)	// Query
                        .setFrom(start).setSize(size).setExplain(true)	//从第几个开始，显示size个数据
                        .highlighter(hiBuilder)		//设置高亮显示
                        .get();
            }else if((priceGt<0||priceLte<0)&&sort.equals("1")){
                searchResponse=client.prepareSearch("item")
                        .setTypes("itemList")
                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                        .setQuery(qb)	// Query
                        .setFrom(start).setSize(size).setExplain(true)	//从第几个开始，显示size个数据
                        .highlighter(hiBuilder)		//设置高亮显示
                        .addSort("salePrice", SortOrder.ASC)
                        .get();
            }else if((priceGt<0||priceLte<0)&&sort.equals("-1")){
                searchResponse=client.prepareSearch("item")
                        .setTypes("itemList")
                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                        .setQuery(qb)	// Query
                        .setFrom(start).setSize(size).setExplain(true)	//从第几个开始，显示size个数据
                        .highlighter(hiBuilder)		//设置高亮显示
                        .addSort("salePrice", SortOrder.DESC)
                        .get();
            }

            SearchHits hits = searchResponse.getHits();
            //返回总结果数
            searchResult.setRecordCount(hits.totalHits);
            List<SearchItem> list=new ArrayList<>();
            if (hits.totalHits > 0) {
                for (SearchHit hit : hits) {
                    System.out.println(hit.toString()+"---------------------ES查询返回的结果--------------------");
                    //总页数
                    int totalPage=(int) (hit.getScore()/size);
                    if((hit.getScore()%size)!=0){
                        totalPage++;
                    }
                    //返回结果总页数
                    searchResult.setTotalPages(totalPage);
                    //设置高亮字段
                    SearchItem searchItem=new Gson().fromJson(hit.getSourceAsString(),SearchItem.class);

                    //这里有两个字段要设置高亮，判空是因为有可能没有找到这个字段，因为设置了三个字段
                    HighlightField highProductName =  hit.getHighlightFields().get("productName");
                    if(highProductName!=null){
                        String productName = hit.getHighlightFields().get("productName").getFragments()[0].toString();
                        searchItem.setProductName(productName);
                    }

                    HighlightField highSubTitle =  hit.getHighlightFields().get("subTitle");
                    if(highSubTitle!=null){
                        String subTitle = hit.getHighlightFields().get("subTitle").getFragments()[0].toString();
                        searchItem.setSubTitle(subTitle);
                    }

                    //返回结果
                    list.add(searchItem);
                }
            }
            searchResult.setItemList(list);
            //client.close();

            return searchResult;
        }catch (Exception e){
            e.printStackTrace();
            throw new SmallException("查询ES索引库出错");
        }
    }
}
