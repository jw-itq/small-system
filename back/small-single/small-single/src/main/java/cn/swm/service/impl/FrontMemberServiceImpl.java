package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.common.jedis.JedisClient;
import cn.swm.mapper.TbMemberMapper;
import cn.swm.pojo.TbMember;
import cn.swm.pojo.front.Member;
import cn.swm.service.FrontMemberService;
import cn.swm.service.LoginService;
import cn.swm.utils.QiniuUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FrontMemberServiceImpl implements FrontMemberService {

    @Autowired
    private TbMemberMapper tbMemberMapper;

    @Autowired
    private LoginService loginService;

    @Autowired
    private JedisClient jedisClient;

    /**
     * 用户头像上传的地方，完全由七牛云来解决
     * @param userId 用户的id
     * @param token 根据token来查找用户的信息，为了更新缓存
     * @param imgData 图片的base64
     * @return
     */
    @Override
    public String imageUpload(long userId, String token, String imgData) {

        //过滤data：URL
        String base64 = QiniuUtil.base64Data(imgData);
        String imgPath = QiniuUtil.qiniuBase64Upload(base64);

        TbMember tbMember = tbMemberMapper.selectByPrimaryKey(userId);
        if(tbMember==null){
            throw new SmallException("在修改用户头像中获取用户的时候失败");
        }

        tbMember.setFile(imgPath);
        if(tbMemberMapper.updateByPrimaryKey(tbMember)!=1){
            throw new SmallException("保存用户的头像失败");
        }

        //这里要更新缓存，因为前台使用的是dto，,根据token获得用户的信息
        Member member = loginService.checkLoginByToken(token);
        member.setFile(imgPath);
        jedisClient.set("SESSION:"+token,new Gson().toJson(member));

        return imgPath;
    }
}
