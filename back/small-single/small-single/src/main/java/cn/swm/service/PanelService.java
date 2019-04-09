package cn.swm.service;

import cn.swm.pojo.TbPanel;
import cn.swm.pojo.common.ZTreeNode;

import java.util.List;

public interface PanelService {

    List<ZTreeNode> getPanelList(int position,boolean showAll);

    int updatePanel(TbPanel tbPanel);

    int addPanel(TbPanel tbPanel);

    int deletePanel(int id);
}
