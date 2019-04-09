package cn.swm.mapper;

import cn.swm.pojo.TbPay;
import cn.swm.pojo.TbPayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbPayMapper {
    long countByExample(TbPayExample example);

    int deleteByExample(TbPayExample example);

    int deleteByPrimaryKey(String id);

    int insert(TbPay record);

    int insertSelective(TbPay record);

    List<TbPay> selectByExample(TbPayExample example);

    TbPay selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TbPay record, @Param("example") TbPayExample example);

    int updateByExample(@Param("record") TbPay record, @Param("example") TbPayExample example);

    int updateByPrimaryKeySelective(TbPay record);

    int updateByPrimaryKey(TbPay record);
}