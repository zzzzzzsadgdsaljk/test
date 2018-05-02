package kesun.controller;

public class LayuiReturnBean {
    private String code="";//返回状态码
    private String message="";//返回信息描述
    private Object data=null;//返回对象
    private String count="";//返回信息描述

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
