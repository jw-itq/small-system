package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.mapper.TbAddressMapper;
import cn.swm.pojo.TbAddress;
import cn.swm.pojo.TbAddressExample;
import cn.swm.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {


    @Autowired
    private TbAddressMapper tbAddressMapper;

    /**
     * 查询当前用户下面的地址信息
     * @return
     */
    @Override
    public List<TbAddress> getAddressList(long userId) {

        List<TbAddress> list = new ArrayList<>();
        TbAddressExample tbAddressExample = new TbAddressExample();
        TbAddressExample.Criteria criteria = tbAddressExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        list =  tbAddressMapper.selectByExample(tbAddressExample);

        if(list==null){
            throw new SmallException("查询用户所有地址失败");
        }

        //把默认地址放在第一个位置
        for(int i = 0;i<list.size();i++){
            if(list.get(i).getIsDefault()){
                Collections.swap(list,0,i);
                break;
            }
        }

        return list;
    }


    /**
     * 修改用户地址的信息
     * @param tbAddress
     * @return
     */
    @Override
    public int updateAddress(TbAddress tbAddress) {

        //设置唯一的默认
        setOneDefault(tbAddress);
        if(tbAddressMapper.updateByPrimaryKey(tbAddress)!=1){
            throw new SmallException("修改用户的地址的时候出错");
        }
        return 1;
    }

    /**
     * 添加收获地址
     * @param tbAddress
     * @return
     */
    @Override
    public int addressAdd(TbAddress tbAddress) {

        setOneDefault(tbAddress);
        if(tbAddressMapper.insert(tbAddress)!=1){
            throw new SmallException("添加收获地址失败");
        }
        return 1;
    }

    /**
     * 根据id删除用户地址
     * @param tbAddress
     * @return
     */
    @Override
    public int deleteAddressById(TbAddress tbAddress) {

        if(tbAddressMapper.deleteByPrimaryKey(tbAddress.getAddressId())!=1){
            throw new SmallException("删除用户的地址失败");
        }
        return 1;
    }

    /**
     * 这个方法是为了设置唯一默认的方法，思路就是首先把所有的要更改的用户的信息的时候，全都先设置城不是默认
     */
    public void setOneDefault(TbAddress tbAddress){
        TbAddressExample tbAddressExample = new TbAddressExample();
        TbAddressExample.Criteria criteria = tbAddressExample.createCriteria();
        criteria.andUserIdEqualTo(tbAddress.getUserId());
        List<TbAddress> list = tbAddressMapper.selectByExample(tbAddressExample);

        for(TbAddress address : list){
            if(address.getIsDefault()){
                address.setIsDefault(false);
                tbAddressMapper.updateByPrimaryKey(tbAddress);
            }
        }
    }
}
