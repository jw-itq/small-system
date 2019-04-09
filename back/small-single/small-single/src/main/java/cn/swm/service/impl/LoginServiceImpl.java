package cn.swm.service.impl;

import cn.swm.common.jedis.JedisClient;
import cn.swm.mapper.TbMemberMapper;
import cn.swm.pojo.TbMember;
import cn.swm.pojo.TbMemberExample;
import cn.swm.pojo.front.Member;
import cn.swm.service.LoginService;
import cn.swm.utils.DtoUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbMemberMapper tbMemberMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;


    /**
     * 判断用户是否登陆,做法就是将前台存储在localstorage中的token拿来跟redis缓存中的token进行比较，
     * 看是不是存在token或者，redis缓存中的token是不是已经过期
     * @param token
     * @return 返回member里面的state状态
     */
    @Override
    public Member checkLoginByToken(String token) {

        String json = jedisClient.get("SESSION:"+token);

        if(json==null){
            Member member = new Member();
            member.setState(0);
            member.setMessage("用户登陆已经过期");
            return member;
        }

        //重至redis的登陆缓存，让它重新倒计时
        jedisClient.expire("SESSION:"+token,SESSION_EXPIRE);

        Member member = new Gson().fromJson(json,Member.class);
        return member;
    }

    /**
     * 用户登陆的方法
     * @param username
     * @param password
     * @return
     */
    @Override
    public Member userLogin(String username, String password) {
        TbMemberExample tbMemberExample = new TbMemberExample();
        TbMemberExample.Criteria criteria = tbMemberExample.createCriteria();
        criteria.andStateEqualTo(1);
        criteria.andUsernameEqualTo(username);
        List<TbMember> list = tbMemberMapper.selectByExample(tbMemberExample);
        Member member = new Member();
        if(list==null||list.size()==0){
            member.setState(0);
            member.setMessage("用户名或者密码错误");
            return member;
        }

        TbMember tbMember = list.get(0);
        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(tbMember.getPassword())){
            member.setState(0);
            member.setMessage("用户名或者密码错误");
            return member;
        }

        // 一切完成验证了之后就说明确实存在这个用户，准备返回成功
        member = DtoUtil.TbMemer2Member(tbMember);
        //生成token
        String token = UUID.randomUUID().toString();
        member.setState(1);
        member.setToken(token);

        // 将用户信息写入到redis缓存中，并设置过期时间
        jedisClient.set("SESSION:"+token,new Gson().toJson(member));
        jedisClient.expire("SESSION:"+token,SESSION_EXPIRE);

        return member;
    }

    /**
     * 用户退出就是删除缓存中对应的token
     * @param token
     * @return
     */
    @Override
    public int loginOut(String token) {
        jedisClient.del("SESSION:"+token);
        return 1;
    }
}
