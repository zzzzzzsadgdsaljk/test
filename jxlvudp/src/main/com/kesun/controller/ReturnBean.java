package kesun.controller;

/**
 * 控制层返回前台的对象
 * Created by wph-pc on 2017/9/8.
 */
public class ReturnBean{
    private String code="";//返回状态码
    private String message="";//返回信息描述
    private Object obj=null;//返回对象

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
