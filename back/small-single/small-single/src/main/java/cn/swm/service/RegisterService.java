package cn.swm.service;

public interface RegisterService {

    int register(String username,String password);

    boolean checkData(String param, int type);
}
