package kesun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wph-pc on 2017/6/11.
 */
@Controller
public class ControllerException extends Exception {
    @RequestMapping("/authorityError")
    public String authorityError(){
        return "authorityError";
    }
}
