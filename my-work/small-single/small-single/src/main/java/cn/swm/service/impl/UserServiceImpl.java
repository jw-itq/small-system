package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.mapper.TbUserMapper;
import cn.swm.pojo.TbUser;
import cn.swm.pojo.TbUserExample;
import cn.swm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public Set<String> getRoles(String userName) {
        return tbUserMapper.getRoles(userName);
    }

    @Override
    public Set<String> getPermissions(String userName) {
        return tbUserMapper.getPermissions(userName);
    }

    @Override
    public TbUser getUserByUsername(String username) {
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andStateEqualTo(1);
        List<TbUser> list = null;
        try {
            list = tbUserMapper.selectByExample(tbUserExample);
        }catch (Exception e){
            throw new SmallException("通过ID获取用户信息失败");
        }
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
}
