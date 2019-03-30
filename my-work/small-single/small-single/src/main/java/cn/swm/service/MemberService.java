package cn.swm.service;

import cn.swm.pojo.TbMember;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.dto.MemberDto;

public interface MemberService {

    /**
     * int draw, int start, int length, @RequestParam("search[value]") String search,
     *   @RequestParam("order[0][column]") int orderCol, @RequestParam("order[0][dir]") String orderDir,
     *    String searchKey,String minDate,String maxDate
     * @return
     */
    DataTableResult getMemberList(int draw, int start, int length,String searchKey,
                                  String minDate,String maxDate,String orderCol,String orderDir);

    DataTableResult getMemberCount();

    TbMember stopMember(long id,int state);

    TbMember getMemberById(long memberId);

    TbMember getMemberByEditUsername(long id,String username);

    TbMember getMemberByEditPhone(long id,String phone);

    TbMember getMemberByEditEmail(long id,String email);

    int updateMember(long id, MemberDto memberDto);

    int updateMemberPassword(long id,MemberDto memberDto);

    int deleteMemberById(long id);

    TbMember getMemberByUsername(String username);

    TbMember getMemberByPhone(String phone);

    TbMember getMemberByEmail(String email);

    int addMember(MemberDto memberDto);

    DataTableResult getMemberRemoveList(int draw, int start, int length,String searchKey,
                                        String minDate,String maxDate,String orderCol,String orderDir);

    DataTableResult getMemberRemoveCount();

    int startRemoveMember(long id);

    int deleteRealMemberById(long id);
}
