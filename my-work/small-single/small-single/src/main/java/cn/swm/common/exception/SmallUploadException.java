package cn.swm.common.exception;

public class SmallUploadException extends RuntimeException {

    private String msg;

    public SmallUploadException(String msg){
        super(msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
