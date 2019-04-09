package cn.swm.service;

import cn.swm.pojo.TbAddress;

import java.util.List;

public interface AddressService {

    List<TbAddress> getAddressList(long userId);

    int updateAddress(TbAddress tbAddress);

    int addressAdd(TbAddress tbAddress);

    int deleteAddressById(TbAddress tbAddress);
}
