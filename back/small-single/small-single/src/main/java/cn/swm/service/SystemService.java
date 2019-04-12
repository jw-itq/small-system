package cn.swm.service;

import cn.swm.pojo.TbBase;
import cn.swm.pojo.TbLog;
import cn.swm.pojo.TbShiroFilter;
import cn.swm.pojo.common.Result;

import java.util.List;

public interface SystemService {

    /**
     * 获得shiro过滤链配置
     * @return
     */
    List<TbShiroFilter> getShiroFilter();

    TbBase getBase();

    int updateBase(TbBase tbBase);

    int getShiroCount();

    int addShiro(TbShiroFilter tbShiroFilter);

    int deleteshiroById(int id);

    int updateShiro(TbShiroFilter tbShiroFilter);

    List<TbLog> getLogList();

    int getLogCount();

    int deleteLogById(int id);

    int addLog(TbLog tbLog);
}
