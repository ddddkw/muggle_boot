package web.controller;

import cn.hutool.core.codec.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dkw
 */
@RestController
public class HelloController {



    @GetMapping("/hello")
    public String hello(){


        return "hello!";
    }

    public static void main(String[] args) {


        String basicAuthorization = "user:460f133f-768a-4b36-b5e9-6b8487cd79d2";
        System.out.println(Base64.encode(basicAuthorization));
    }
}