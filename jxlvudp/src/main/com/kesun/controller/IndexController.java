package kesun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wph-pc on 2017/6/11.
 */
@Controller
public class IndexController {
    @RequestMapping(value={"/index","/"},method = RequestMethod.GET)
    public String index(){
        return "../index";
    }

    @RequestMapping("/login")
    public String login(){
        return "/login2.jsp";
    }
}
