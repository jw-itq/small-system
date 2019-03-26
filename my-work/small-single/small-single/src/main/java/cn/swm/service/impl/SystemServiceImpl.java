package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.mapper.TbShiroFilterMapper;
import cn.swm.pojo.TbShiroFilter;
import cn.swm.pojo.TbShiroFilterExample;
import cn.swm.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private TbShiroFilterMapper tbShiroFilterMapper;

    @Override
    public List<TbShiroFilter> getShiroFilter() {
        TbShiroFilterExample tbShiroFilterExample = new TbShiroFilterExample();
        //按指定的字段排序
        tbShiroFilterExample.setOrderByClause("sort_order");
        List<TbShiroFilter> result = tbShiroFilterMapper.selectByExample(tbShiroFilterExample);
        if(result == null){
            throw new SmallException("获取shiro过滤链失败");
        }
        return result;
    }
}
