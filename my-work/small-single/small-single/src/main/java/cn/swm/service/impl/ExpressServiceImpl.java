package cn.swm.service.impl;

import cn.swm.mapper.TbExpressMapper;
import cn.swm.pojo.TbExpress;
import cn.swm.pojo.TbExpressExample;
import cn.swm.service.ExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpressServiceImpl implements ExpressService {

    @Autowired
    private TbExpressMapper tbExpressMapper;

    /**
     * 获得所有快递的信息
     * @return
     */
    @Override
    public List<TbExpress> getExpressList() {
        TbExpressExample tbExpressExample = new TbExpressExample();
        tbExpressExample.setOrderByClause("sort_order asc");
        List<TbExpress> list = tbExpressMapper.selectByExample(tbExpressExample);
        return list;
    }
}
