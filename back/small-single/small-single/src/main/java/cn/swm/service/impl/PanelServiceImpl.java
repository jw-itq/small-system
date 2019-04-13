package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.common.jedis.JedisClient;
import cn.swm.mapper.TbPanelMapper;
import cn.swm.pojo.TbPanel;
import cn.swm.pojo.TbPanelExample;
import cn.swm.pojo.common.Result;
import cn.swm.pojo.common.ZTreeNode;
import cn.swm.service.PanelService;
import cn.swm.utils.DtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PanelServiceImpl implements PanelService {

    @Autowired
    private TbPanelMapper tbPanelMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${PRODUCT_HOME}")
    private String PRODUCT_HOME;

    /**
     * 根据position和showAll的状态来查询要在前台显示的分类内容
     * 在前台需要显示的有这么几种情况：
     * 1，显示所有的分类内容，也就是首页所有的板块内容（将首页分成了若干个板块来统一管理）
     * 2，只显示轮播图来进行管理
     * 3，显示除了轮播图以外的内容板块
     * 4，这一条是作者自加的一个捐赠的板块，所以属于其他板块，我在这里暂且忽略,因为只需要改变position的值
     *
     * 所以我现在要关心的就是1，2，3种情况，
     * @param position 这里首先是controller那边传过来的自定义的值：0;-1;还有可能是其它板块
     * @param showAll 表示是否含有轮播图
     *
     *                通过判断position和showall来判断查询的内容
     *                1,显示所有的内容，首先position=0,然后showAll=true（表示含有轮播图）
     *                2,只显示轮播图，这里为了跟数据库里面的positong字段区分开来，用-1表示只含有轮播图
     *                3,除了轮播图意外的所有板块，让showAll=false就是这个了
     * @return 最后通过DtoUtil里面的TbPanel2ZTreeNode的方法进行转换成ZTreeNode对象放入盗list集合里面
     */
    @Override
    public List<ZTreeNode> getPanelList(int position, boolean showAll) {
        TbPanelExample tbPanelExample = new TbPanelExample();
        TbPanelExample.Criteria criteria = tbPanelExample.createCriteria();
        //不含有轮播图
        if(position==0&&!showAll){
            criteria.andTypeNotEqualTo(0);
        }else if(position==-1){
            //只含有轮播图的情况
            //要将position重新设置为0表示首页所有分类
            position = 0;
            criteria.andTypeEqualTo(0);
        }
        //position标示的是哪种类型的板块
        criteria.andPositionEqualTo(position);

        //设置排序的字段
        tbPanelExample.setOrderByClause("sort_order");

        List<TbPanel> list = tbPanelMapper.selectByExample(tbPanelExample);

        //转换成ZTreeNode集合
        List<ZTreeNode> result = new ArrayList<>();
        for(TbPanel tbPanel : list){
            ZTreeNode zTreeNode = DtoUtil.TbPanel2ZTreeNode(tbPanel);
            result.add(zTreeNode);
        }
        return result;
    }

    /**
     * 根据前台提交的表单数据来更新板块的内容，分类的内容
     * @param tbPanel
     * @return
     */
    @Override
    public int updatePanel(TbPanel tbPanel) {
        TbPanel old = tbPanelMapper.selectByPrimaryKey(tbPanel.getId());
        tbPanel.setCreated(old.getCreated());
        if(tbPanelMapper.updateByPrimaryKey(tbPanel)!=1){
            throw new SmallException("更新板块内容的时候出问题了");
        }
        //同步到缓存
        deleteHomeRedis();
        return 1;
    }

    /**
     * 根据前台提交的内容，进行添加首页分类的内容
     * @param tbPanel
     * @return
     */
    @Override
    public int addPanel(TbPanel tbPanel) {
        //轮播图只能添加一个
        if(tbPanel.getType()==0){
            TbPanelExample tbPanelExample = new TbPanelExample();
            TbPanelExample.Criteria criteria = tbPanelExample.createCriteria();
            criteria.andTypeEqualTo(0);
            List<TbPanel> list = tbPanelMapper.selectByExample(tbPanelExample);
            if(list!=null&&list.size()>0){
                throw new SmallException("首页轮播图只能添加一个");
            }
        }

        tbPanel.setCreated(new Date());
        tbPanel.setUpdated(new Date());

        if(tbPanelMapper.insert(tbPanel)!=1){
            throw new SmallException("添加首页内容板块的时候出错");
        }

        //同步缓存
        deleteHomeRedis();
        return 1;
    }

    /**
     * 根据id来删除板块的内容
     * @param id
     * @return
     */
    @Override
    public int deletePanel(int id) {
        if(tbPanelMapper.deleteByPrimaryKey(id)!=1){
            throw new SmallException("删除首页板块内容的时候出错");
        }
        //更新缓存
        deleteHomeRedis();
        return 1;
    }

    /**
     * 同步首页的缓存
     */
    private void deleteHomeRedis() {
        try {
            jedisClient.del(PRODUCT_HOME);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
