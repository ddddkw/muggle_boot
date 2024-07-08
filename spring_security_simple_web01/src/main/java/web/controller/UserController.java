package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import web.form.CreateUserForm;
import web.service.UserService;

/**
 * @author dkw
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello(){
        // 一旦AuthenticationManager成功完成身份验证，它将为请求的其余部分存储Authentication实例，这个实例就被称为安全上下文
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        System.out.println(authentication);
        return "hello，"+authentication.getName()+"!";
    }

    @PostMapping("/create")
    public Boolean create(@RequestBody CreateUserForm user){
        return userService.createUser(user);
    }
}