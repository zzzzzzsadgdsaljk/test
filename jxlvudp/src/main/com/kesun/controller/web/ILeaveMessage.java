package kesun.controller.web;

import kesun.controller.IController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wph-pc on 2017/5/30.
 */
public interface ILeaveMessage extends IController {
    public void response(HttpServletRequest request, HttpServletResponse response);//留言信息反馈
    public void findResponseMSG(HttpServletRequest request, HttpServletResponse response);//获取反馈信息
}
