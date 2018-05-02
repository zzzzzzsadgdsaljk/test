package kesun.dao.web;


import kesun.entity.web.LeaveMessage;

import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/5/30.
 */
public interface ILeaveMessage {
    int response(String content);//留言信息反馈
    List<LeaveMessage> findResponseMSG(Map<String, Object> conValus);//获取反馈信息
}
