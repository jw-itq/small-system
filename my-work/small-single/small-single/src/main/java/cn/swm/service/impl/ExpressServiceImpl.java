package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
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

    /**
     * 根据id删除快递的信息
     * @param id
     * @return
     */
    @Override
    public int deleteExpressById(int id) {
        if(tbExpressMapper.deleteByPrimaryKey(id)!=1){
            throw new SmallException("删除物流信息的时候出错");
        }
        return 1;
    }

    /**
     * 添加快递的信息
     * @param tbExpress
     * @return
     */
    @Override
    public int addExpress(TbExpress tbExpress) {
        if(tbExpressMapper.insert(tbExpress)!=1){
            throw new SmallException("添加快递信息失败");
        }
        return 1;
    }

    /**
     * 修改快递的信息
     * @param tbExpress
     * @return
     */
    @Override
    public int updateExpress(TbExpress tbExpress) {
        if(tbExpressMapper.updateByPrimaryKey(tbExpress)!=1){
            throw new SmallException("修改快递信息的时候出错了");
        }
        return 1;
    }
}
