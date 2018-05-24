package kesun.entity.web;

import kesun.entity.AbsBusinessObject;

/**
 * Created by 超神归来 on 2018/5/22.
 */
public class Comment extends AbsBusinessObject {
    private String com_time;
    private String com_value;
    private Music mus = null;

    public String getCom_time() {
        return com_time;
    }

    public void setCom_time(String com_time) {
        this.com_time = com_time;
    }

    public String getCom_value() {
        return com_value;
    }

    public void setCom_value(String com_value) {
        this.com_value = com_value;
    }

    public Music getMus() {
        return mus;
    }

    public void setMus(Music mus) {
        this.mus = mus;
    }
}
