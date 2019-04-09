package cn.swm.service;

import cn.swm.pojo.front.Member;

public interface FrontMemberService {

    String imageUpload(long userId,String token,String imgData);
}
