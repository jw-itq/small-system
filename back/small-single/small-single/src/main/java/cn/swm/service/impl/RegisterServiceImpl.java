package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.mapper.TbMemberMapper;
import cn.swm.pojo.TbMember;
import cn.swm.pojo.TbMemberExample;
import cn.swm.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbMemberMapper tbMemberMapper;

    /**
     * 会员注册
     * @param username
     * @param password
     * @return
     */
    @Override
    public int register(String username, String password) {
        TbMember tbMember = new TbMember();
        tbMember.setUsername(username);

        if(username.isEmpty()||password.isEmpty()){
            return -1;//用户名或者密码不能是空
        }

        boolean result = checkData(username,1);
        if(!result){
            return 0;//用户名已经注册
        }

        //md5加密
        String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
        tbMember.setPassword(md5Pass);
        tbMember.setUpdated(new Date());
        tbMember.setCreated(new Date());
        tbMember.setState(1);

        if(tbMemberMapper.insert(tbMember)!=1){
            throw new SmallException("用户注册失败");
        }
        return 1;
    }

    /**
     * 用来判断用户名，邮箱，手机号是不是已经存在了
     * @param param
     * @param type 1用户名,2手机号，3邮箱
     * @return 返回true标示可以注册，false标示被在数据库中找到了，不能重复注册
     */
    @Override
    public boolean checkData(String param, int type) {
        TbMemberExample tbMemberExample = new TbMemberExample();
        TbMemberExample.Criteria criteria = tbMemberExample.createCriteria();

        //1：用户名，2：手机号 3：邮箱
        if(type==1){
            criteria.andUsernameEqualTo(param);
        }else if(type==2){
            criteria.andPhoneEqualTo(param);
        }else if(type==3){
            criteria.andEmailEqualTo(param);
        }else {
            return false;
        }

        List<TbMember> list = tbMemberMapper.selectByExample(tbMemberExample);
        if(list!=null&&list.size()>0){
            return false;
        }
        return true;
    }
}
