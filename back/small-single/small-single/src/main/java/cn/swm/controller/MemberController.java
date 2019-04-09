package cn.swm.controller;

import cn.swm.pojo.TbMember;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.common.Result;
import cn.swm.pojo.dto.MemberDto;
import cn.swm.service.MemberService;
import cn.swm.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;


    /**
     *            "ajax": {
     *                 url:"member/list",
     *                 type: 'GET',
     *                 data:{
     *                     "searchKey": "",
     *                     "minDate": "",
     *                     "maxDate": "",
     *                 },
     *             },
     * @return
     */
    @RequestMapping(value = "member/list",method = RequestMethod.GET)
    public DataTableResult getMemberList(int draw, int start, int length, @RequestParam("search[value]") String search,
                                         @RequestParam("order[0][column]") int orderCol, @RequestParam("order[0][dir]") String orderDir,
                                         String searchKey,String minDate,String maxDate){
        //获取客户端需要排序的列
        String[] cols = {"checkbox","id", "username","sex", "phone", "email", "address", "created", "updated", "state"};
        String orderColumn = cols[orderCol];
        if(orderColumn==null){
            orderColumn = "created";
        }
        if(orderDir==null){
            orderDir = "desc";
        }
        if(!search.isEmpty()){
            searchKey = search;
        }

        DataTableResult result = memberService.getMemberList(draw,start,length,searchKey,minDate,maxDate,orderColumn,orderDir);
        return result;
    }

    @RequestMapping(value = "/member/count",method = RequestMethod.GET)
    public DataTableResult getMemberCount(){
        return memberService.getMemberCount();
    }

    @RequestMapping(value = "/member/stop/{id}",method = RequestMethod.PUT)
    public Result<TbMember> stopMember(@PathVariable("id")long id){
        TbMember tbMember = memberService.stopMember(id,0);
        return new ResultUtil<TbMember>().setData(tbMember);
    }

    /*@RequestMapping(value = "/member/start/{id}",method = RequestMethod.PUT)
    public Result<TbMember> startMember(@PathVariable("id")long id){
        TbMember tbMember = memberService.stopMember(id,1);
        return new ResultUtil<TbMember>().setData(tbMember);
    }*/

    @RequestMapping(value = "/member/edit/{id}/username",method = RequestMethod.GET)
    public Boolean validateEditUsername(@PathVariable("id")long id,String username){
        if(memberService.getMemberByEditUsername(id,username)!=null){
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/member/edit/{id}/phone",method = RequestMethod.GET)
    public Boolean validateEditPhone(@PathVariable("id")long id,String phone){
        if(memberService.getMemberByEditPhone(id,phone)!=null){
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/member/edit/{id}/email",method = RequestMethod.GET)
    public Boolean validateEditEmail(@PathVariable("id")long id,String email){
        if(memberService.getMemberByEditEmail(id,email)!=null){
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/member/update/{id}",method = RequestMethod.POST)
    public Result<Object> updateMember(@PathVariable("id")long id,@ModelAttribute MemberDto memberDto){
        memberService.updateMember(id,memberDto);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/member/changePass/{id}",method = RequestMethod.POST)
    public Result<Object> updateMemberPassword(@PathVariable("id")long id,@RequestParam MemberDto memberDto){
        memberService.updateMemberPassword(id,memberDto);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/member/remove/{ids}",method = RequestMethod.PUT)
    public Result<Object> deleteMemberById(@PathVariable("ids")long[] ids){
        for(long id : ids){
            memberService.deleteMemberById(id);
        }
        return new ResultUtil<Object>().setData(null);
    }


    @RequestMapping(value = "/member/username",method = RequestMethod.GET)
    @ApiOperation(value = "验证注册名是否存在")
    public Boolean validateUsername(String username){

        if(memberService.getMemberByUsername(username)!=null){
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/member/phone",method = RequestMethod.GET)
    @ApiOperation(value = "验证注册手机是否存在")
    public Boolean validatePhone(String phone){

        if(memberService.getMemberByPhone(phone)!=null){
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/member/email",method = RequestMethod.GET)
    @ApiOperation(value = "验证注册邮箱是否存在")
    public Boolean validateEmail(String email){

        if(memberService.getMemberByEmail(email)!=null){
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/member/add",method = RequestMethod.POST)
    public Result<Object> addMember(@ModelAttribute MemberDto memberDto){
        memberService.addMember(memberDto);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "member/list/remove",method = RequestMethod.GET)
    public DataTableResult getMemberRemoveList(int draw, int start, int length, @RequestParam("search[value]") String search,
                                               @RequestParam("order[0][column]") int orderCol, @RequestParam("order[0][dir]") String orderDir,
                                               String searchKey,String minDate,String maxDate){
        //获取客户端需要排序的列
        String[] cols = {"checkbox","id", "username","sex", "phone", "email", "address", "created", "updated", "state"};
        String orderColumn = cols[orderCol];
        if(orderColumn==null){
            orderColumn = "created";
        }
        if(orderDir==null){
            orderDir = "desc";
        }
        if(!search.isEmpty()){
            searchKey = search;
        }

        DataTableResult result = memberService.getMemberRemoveList(draw,start,length,searchKey,minDate,maxDate,orderColumn,orderDir);

        return result;
    }

    @RequestMapping(value = "/member/count/remove",method = RequestMethod.GET)
    public DataTableResult getRemoveCountList(){
        return memberService.getMemberRemoveCount();
    }

    @RequestMapping(value = "/member/start/{ids}",method = RequestMethod.PUT)
    public Result<Object> startRemoveMember(@PathVariable("ids")long[] ids){
        for(long id : ids){
            memberService.startRemoveMember(id);
        }
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/member/del/{ids}",method = RequestMethod.DELETE)
    public Result<Object> deleteRealMemberById(@PathVariable("ids")long[] ids){
        for(long id : ids){
            memberService.deleteRealMemberById(id);
        }
        return new ResultUtil<Object>().setData(null);
    }

}
