package cn.swm.service.impl;

import cn.swm.common.constant.DictConstant;
import cn.swm.mapper.TbDictMapper;
import cn.swm.pojo.TbDict;
import cn.swm.pojo.TbDictExample;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private TbDictMapper tbDictMapper;

    /**
     * 获得所有扩展词库
     * @return
     */
    @Override
    public List<TbDict> getDictList() {
        TbDictExample tbDictExample = new TbDictExample();
        TbDictExample.Criteria criteria = tbDictExample.createCriteria();

        criteria.andTypeEqualTo(DictConstant.DICT_EXT);
        List<TbDict> list = tbDictMapper.selectByExample(tbDictExample);

        return list;
    }

    /**
     * 编辑字典
     * @param tbDict
     * @return
     */
    @Override
    public int editDict(TbDict tbDict) {
        tbDictMapper.updateByPrimaryKey(tbDict);
        return 1;
    }

    /**
     * 删除字典库
     * @param id
     * @return
     */
    @Override
    public int deleteDict(int id) {
        tbDictMapper.deleteByPrimaryKey(id);
        return 1;
    }

    /**
     * 获得所有的停用词库
     * @return
     */
    @Override
    public List<TbDict> getStopDictList() {
        TbDictExample tbDictExample = new TbDictExample();
        TbDictExample.Criteria criteria = tbDictExample.createCriteria();
        criteria.andTypeEqualTo(DictConstant.DICT_STOP);
        List<TbDict> list = tbDictMapper.selectByExample(tbDictExample);
        return list;
    }

    /**
     * 添加词典
     * @param tbDict
     * @return
     */
    @Override
    public int addDict(TbDict tbDict) {
        tbDictMapper.insert(tbDict);
        return 1;
    }
}
