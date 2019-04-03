package cn.swm.common.exception;

public class SmallException extends RuntimeException{

    private String msg;

    public SmallException(String msg){
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
