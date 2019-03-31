package cn.swm.service;

import cn.swm.pojo.TbExpress;

import java.util.List;

public interface ExpressService {

    List<TbExpress> getExpressList();

    int deleteExpressById(int id);

    int addExpress(TbExpress tbExpress);

    int updateExpress(TbExpress tbExpress);
}
