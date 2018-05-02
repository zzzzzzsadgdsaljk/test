package kesun.controller;

/**
 * Created by wph-pc on 2017/10/4.
 */
public class ReturnBeanInPower extends ReturnBean {
    private boolean[] hasPower=null;//是否有指定权限

    public boolean[] getHasPower() {
        return hasPower;
    }

    public void setHasPower(boolean[] hasPower) {
        this.hasPower = hasPower;
    }
}
