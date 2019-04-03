package cn.swm.service;

import cn.swm.pojo.TbDict;
import cn.swm.pojo.common.DataTableResult;

import java.util.List;

public interface DictService {

    List<TbDict> getDictList();

    int editDict(TbDict tbDict);

    int deleteDict(int id);

    List<TbDict> getStopDictList();

    int addDict(TbDict tbDict);
}
