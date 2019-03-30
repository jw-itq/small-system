package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.mapper.TbMemberMapper;
import cn.swm.pojo.TbMember;
import cn.swm.pojo.TbMemberExample;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.dto.MemberDto;
import cn.swm.service.MemberService;
import cn.swm.utils.DtoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private TbMemberMapper tbMemberMapper;

    /**
     * 多条件查询并分页显示会员列表
     * @param draw
     * @param start
     * @param length
     * @param searchKey
     * @param minDate
     * @param maxDate
     * @param orderCol
     * @param orderDir
     * @return
     */
    @Override
    public DataTableResult getMemberList(int draw, int start, int length, String searchKey,
                                         String minDate, String maxDate, String orderCol, String orderDir) {
        DataTableResult result = new DataTableResult();
        try {
            List<TbMember> list = tbMemberMapper.getSearchMemberList(searchKey,minDate,maxDate,orderCol,orderDir);
            PageHelper.startPage(start/length+1,length);
            PageInfo pageInfo = new PageInfo(list);
            result.setRecordsFiltered((int) pageInfo.getTotal());
            result.setData(list);
            result.setDraw(draw);
            result.setRecordsTotal(getMemberCount().getRecordsTotal());
        }catch (Exception e){
            e.printStackTrace();
            throw new SmallException("加载用户数据失败");
        }

        return result;
    }

    /**
     * 获得用户的总数量
     * @return
     */
    @Override
    public DataTableResult getMemberCount() {
        DataTableResult result = new DataTableResult();
        TbMemberExample tbMemberExample = new TbMemberExample();
        TbMemberExample.Criteria criteria = tbMemberExample.createCriteria();
        criteria.andStateNotEqualTo(2);
        try {
            result.setRecordsTotal((int) tbMemberMapper.countByExample(tbMemberExample));
        }catch (Exception e){
            e.printStackTrace();
            throw new SmallException("统计移除会员数量失败");
        }

        return result;
    }

    /**
     * 根据id更改用户的状态为停用
     * @param id
     * @param state
     * @return
     */
    @Override
    public TbMember stopMember(long id, int state) {
        TbMember tbMember = tbMemberMapper.selectByPrimaryKey(id);
        tbMember.setState(state);
        tbMember.setUpdated(new Date());
        tbMemberMapper.updateByPrimaryKey(tbMember);
        return getMemberById(id);
    }

    /**
     * 根据id获得会员的信息
     * @param memberId
     * @return
     */
    @Override
    public TbMember getMemberById(long memberId) {
        TbMember tbMember = null;
        try {
            tbMember = tbMemberMapper.selectByPrimaryKey(memberId);
        }catch (Exception e){
            e.printStackTrace();
            throw new SmallException("根据id获得会员信息失败");
        }
        return tbMember;
    }

    /**
     * 验证用户是否存在
     * @param id
     * @param username
     * @return
     */
    @Override
    public TbMember getMemberByEditUsername(long id, String username) {
        TbMember oldMember = tbMemberMapper.selectByPrimaryKey(id);
        TbMember newMember = null;
        if(oldMember.getUsername()==null||!oldMember.getUsername().equals(username)){
            newMember = getMemberByUsername(username);
        }
        return newMember;
    }

    /**
     * 根据用户名查找会员信息
     * @param username
     * @return
     */
    @Override
    public TbMember getMemberByUsername(String username) {
        TbMemberExample tbMemberExample = new TbMemberExample();
        TbMemberExample.Criteria criteria = tbMemberExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbMember> list = tbMemberMapper.selectByExample(tbMemberExample);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    /**
     * 验证手机是否存在
     * @param id
     * @param phone
     * @return
     */
    @Override
    public TbMember getMemberByEditPhone(long id, String phone) {
        TbMember oldMember = tbMemberMapper.selectByPrimaryKey(id);
        TbMember newMember = null;
        if(oldMember.getPhone()==null||!oldMember.getPhone().equals(phone)){
            newMember = getMemberByPhone(phone);
        }
        return newMember;
    }

    /**
     * 根据电话来查找会员信息
     * @param phone
     * @return
     */
    @Override
    public TbMember getMemberByPhone(String phone) {
        TbMemberExample tbMemberExample = new TbMemberExample();
        TbMemberExample.Criteria criteria = tbMemberExample.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<TbMember> list = tbMemberMapper.selectByExample(tbMemberExample);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    /**
     * 验证邮箱是否存在
     * @param id
     * @param email
     * @return
     */
    @Override
    public TbMember getMemberByEditEmail(long id, String email) {
        TbMember oldMember = tbMemberMapper.selectByPrimaryKey(id);
        TbMember newMember = null;
        if(oldMember.getEmail()==null||!oldMember.getEmail().equals(email)){
            newMember = getMemberByEmail(email);
        }
        return newMember;
    }

    /**
     * 修改会员的信息
     * @param id
     * @param memberDto
     * @return
     */
    @Override
    public int updateMember(long id, MemberDto memberDto) {
        TbMember member = DtoUtil.MemberDto2Member(memberDto);
        member.setId(id);
        member.setUpdated(new Date());

        TbMember oldMember = getMemberById(id);
        member.setState(oldMember.getState());
        member.setCreated(oldMember.getCreated());

        if(member.getPassword()==null||member.getPassword()==""){
            member.setPassword(oldMember.getPassword());
        }else {
            String md5Pass = DigestUtils.md5DigestAsHex(member.getPassword().getBytes());
            member.setPassword(md5Pass);
        }

        if(tbMemberMapper.updateByPrimaryKey(member)!=1){
            throw new SmallException("更新会员的信息出错");
        }

        return 1;
    }

    /**
     * 修改会员的密码
     * @param id
     * @param memberDto
     * @return
     */
    @Override
    public int updateMemberPassword(long id, MemberDto memberDto) {
        TbMember tbMember = DtoUtil.MemberDto2Member(memberDto);
        TbMember oldMember = tbMemberMapper.selectByPrimaryKey(id);

        String md5Pass = DigestUtils.md5DigestAsHex(tbMember.getPassword().getBytes());
        oldMember.setPassword(md5Pass);
        oldMember.setUpdated(new Date());
        if(tbMemberMapper.updateByPrimaryKey(oldMember)!=1){
            throw new SmallException("修改密码出错");
        }
        return 1;
    }

    /**
     * 根据id删除用户的信息，应该就是白状态类型改变城2
     * @param id
     * @return
     */
    @Override
    public int deleteMemberById(long id) {
        TbMember tbMember = tbMemberMapper.selectByPrimaryKey(id);
        tbMember.setState(2);
        if(tbMemberMapper.updateByPrimaryKey(tbMember)!=1){
            throw new SmallException("删除(更新)用户信息的时候出错了");
        }
        return 1;
    }

    /**
     * 根据邮箱查询会员的信息
     * @param email
     * @return
     */
    @Override
    public TbMember getMemberByEmail(String email) {
        TbMemberExample tbMemberExample = new TbMemberExample();
        TbMemberExample.Criteria criteria = tbMemberExample.createCriteria();
        criteria.andEmailEqualTo(email);
        List<TbMember> list = tbMemberMapper.selectByExample(tbMemberExample);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    /**
     * 添加会员信息
     * @param memberDto
     * @return
     */
    @Override
    public int addMember(MemberDto memberDto) {
        TbMember tbMember = DtoUtil.MemberDto2Member(memberDto);

        if(getMemberByUsername(tbMember.getUsername())!=null){
            throw new SmallException("用户名已经注册");
        }

        if(getMemberByPhone(tbMember.getPhone())!=null){
            throw new SmallException("手机号已经存在");
        }

        if(getMemberByEmail(tbMember.getEmail())!=null){
            throw new SmallException("邮箱已经存在");
        }

        tbMember.setUpdated(new Date());
        tbMember.setCreated(new Date());
        tbMember.setState(1);
        String md5Pass = DigestUtils.md5DigestAsHex(tbMember.getPassword().getBytes());
        tbMember.setPassword(md5Pass);

        if(tbMemberMapper.insert(tbMember)!=1){
            throw new SmallException("添加会员信息失败");
        }
        return 1;
    }

    /**
     * 多条件分页查询已经删除的会员的信息
     * @param draw
     * @param start
     * @param length
     * @param searchKey
     * @param minDate
     * @param maxDate
     * @param orderCol
     * @param orderDir
     * @return
     */
    @Override
    public DataTableResult getMemberRemoveList(int draw, int start, int length, String searchKey, String minDate, String maxDate, String orderCol, String orderDir) {
        DataTableResult result = new DataTableResult();
        try {
            List<TbMember> list = tbMemberMapper.getSearchRemoveMemberList(searchKey,minDate,maxDate,orderCol,orderDir);
            PageHelper.startPage(start/length+1,length);
            PageInfo pageInfo = new PageInfo(list);
            result.setRecordsFiltered((int) pageInfo.getTotal());
            result.setData(list);
            result.setDraw(draw);
            result.setRecordsTotal(getMemberRemoveCount().getRecordsTotal());
        }catch (Exception e){
            e.printStackTrace();
            throw new SmallException("加载删除用户数据失败");
        }

        return result;
    }

    /**
     * 得到删除会员的数量
     * @return
     */
    @Override
    public DataTableResult getMemberRemoveCount() {
        DataTableResult result = new DataTableResult();
        TbMemberExample tbMemberExample = new TbMemberExample();
        TbMemberExample.Criteria criteria = tbMemberExample.createCriteria();
        criteria.andStateEqualTo(2);
        try {
            result.setRecordsTotal((int) tbMemberMapper.countByExample(tbMemberExample));
        }catch (Exception e){
            e.printStackTrace();
            throw new SmallException("获得删除用户的数量的时候出错");
        }
        return result;
    }

    /**
     * 还原删除的会员信息,就是将会员信息的state设置为1
     * @param id
     * @return
     */
    @Override
    public int startRemoveMember(long id) {
        TbMember tbMember = tbMemberMapper.selectByPrimaryKey(id);
        tbMember.setState(1);
        tbMember.setUpdated(new Date());
        if(tbMemberMapper.updateByPrimaryKey(tbMember)!=1){
            throw new SmallException("还原删除用户的信息的时候出错");
        }
        return 1;
    }

    /**
     * 彻底的删除会员的信息，根据id
     * @param id
     * @return
     */
    @Override
    public int deleteRealMemberById(long id) {
        if(tbMemberMapper.deleteByPrimaryKey(id)!=1){
            throw new SmallException("彻底删除用户的信息的是出错");
        }
        return 1;
    }
}
