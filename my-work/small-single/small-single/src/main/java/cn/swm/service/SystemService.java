package cn.swm.service;

import cn.swm.pojo.TbShiroFilter;

import java.util.List;

public interface SystemService {

    /**
     * 获得shiro过滤链配置
     * @return
     */
    List<TbShiroFilter> getShiroFilter();
}
