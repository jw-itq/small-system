package cn.swm.service;

import cn.swm.pojo.TbPanelContent;
import cn.swm.pojo.common.DataTableResult;

public interface ContentService {

    DataTableResult getPanelContentListByPanelId(int panelId);

    int addContent(TbPanelContent tbPanelContent);

    int deletePanelContent(int id);

    int updatePanelContent(TbPanelContent tbPanelContent);
}
