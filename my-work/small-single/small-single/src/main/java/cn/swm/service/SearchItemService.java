package cn.swm.service;

import cn.swm.pojo.dto.EsInfo;

public interface SearchItemService {

    int refreshItem(int type,long itemId);

    EsInfo getESInfo();

    int importIndex();
}
