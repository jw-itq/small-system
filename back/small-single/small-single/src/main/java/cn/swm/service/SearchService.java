package cn.swm.service;

import cn.swm.pojo.front.SearchResult;

public interface SearchService {

    SearchResult searchProduct(String key,int size,int page,String sort,int priceGt,int priceLte);
}
