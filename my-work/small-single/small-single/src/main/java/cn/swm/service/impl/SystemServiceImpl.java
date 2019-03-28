package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.mapper.TbBaseMapper;
import cn.swm.mapper.TbShiroFilterMapper;
import cn.swm.pojo.TbBase;
import cn.swm.pojo.TbShiroFilter;
import cn.swm.pojo.TbShiroFilterExample;
import cn.swm.pojo.common.Result;
import cn.swm.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private TbShiroFilterMapper tbShiroFilterMapper;

    @Autowired
    private TbBaseMapper tbBaseMapper;

    @Value("1")
    private String BASE_ID;

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

    /**
     * 效果就是首页刚开始进去的那个弹框
     * @return
     */
    @Override
    public TbBase getBase() {
        TbBase tbBase = tbBaseMapper.selectByPrimaryKey(Integer.parseInt(BASE_ID));
        if(tbBase == null){
            throw new SmallException("获取基础设置失败");
        }
        return tbBase;
    }
}
