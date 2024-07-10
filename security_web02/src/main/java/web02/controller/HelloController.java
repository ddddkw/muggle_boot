package web02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author dkw
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){

        return "hello world";
    }

    @GetMapping("/xiao")
    @ResponseBody
    public String xiao(){

        return "纳西妲我抽爆！";
    }

    @GetMapping("/a")
    @ResponseBody
    public String a(){

        return "纳西妲我抽爆！";
    }

    @PostMapping("/a")
    @ResponseBody
    public String b(){

        return "纳西妲我抽爆！";
    }
}
