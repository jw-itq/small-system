package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.mapper.TbBaseMapper;
import cn.swm.mapper.TbLogMapper;
import cn.swm.mapper.TbShiroFilterMapper;
import cn.swm.pojo.*;
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

    @Autowired
    private TbLogMapper tbLogMapper;

    @Value("${BASE_ID}")
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

    /**
     * 修改基本的配置
     * @param tbBase
     * @return
     */
    @Override
    public int updateBase(TbBase tbBase) {
        if(tbBaseMapper.updateByPrimaryKey(tbBase)!=1){
            throw new SmallException("更新基本的设置的时候出错");
        }
        return 1;
    }

    /**
     * 获得shiro权限表的总数
     * @return
     */
    @Override
    public int getShiroCount() {
        TbShiroFilterExample tbShiroFilterExample = new TbShiroFilterExample();
        return (int) tbShiroFilterMapper.countByExample(tbShiroFilterExample);
    }

    /**
     * 添加shiro
     * @param tbShiroFilter
     * @return
     */
    @Override
    public int addShiro(TbShiroFilter tbShiroFilter) {
        if(tbShiroFilterMapper.insert(tbShiroFilter)!=1){
            throw new SmallException("添加shiro过滤出错");
        }
        return 1;
    }

    /**
     * 根据id删除shiro的信息
     * @param id
     * @return
     */
    @Override
    public int deleteshiroById(int id) {
        if(tbShiroFilterMapper.deleteByPrimaryKey(id)!=1){
            throw new SmallException("删除shiro失败");
        }
        return 1;
    }

    /**
     * 修改权限的信息
     * @param tbShiroFilter
     * @return
     */
    @Override
    public int updateShiro(TbShiroFilter tbShiroFilter) {
        if(tbShiroFilterMapper.updateByPrimaryKey(tbShiroFilter)!=1){
            throw new SmallException("修改shiro权限的时候出错");
        }
        return 1;
    }

    /**
     * 得到系统日志的信息
     * @return
     */
    @Override
    public List<TbLog> getLogList() {
        TbLogExample tbLogExample = new TbLogExample();
        return tbLogMapper.selectByExample(tbLogExample);
    }

    /**
     * 得到系统日志的数量
     * @return
     */
    @Override
    public int getLogCount() {
        TbLogExample tbLogExample = new TbLogExample();
        return (int) tbLogMapper.countByExample(tbLogExample);
    }

    /**
     * 根据id来删除日志信息
     * @param id
     * @return
     */
    @Override
    public int deleteLogById(int id) {
        if(tbLogMapper.deleteByPrimaryKey(id)!=1){
            throw new SmallException("根据id删除日志信息失败");
        }
        return 1;
    }
}
