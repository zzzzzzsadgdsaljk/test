package kesun.entity;

/**
 * Created by wph-pc on 2017/10/26.
 */
public class LayuiUploadImage {
    private String code="";
    private String msg="";
    private LayuiUploadImageItem data=null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LayuiUploadImageItem getData() {
        return data;
    }

    public void setData(LayuiUploadImageItem data) {
        this.data = data;
    }
}
