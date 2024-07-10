package web02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import web02.common.UserConstants;
import web02.config.Result;
import web02.entity.User;
import web02.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Result createUser(@RequestBody User user){


        if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))){


            return Result.error().message("手机号已存在");
        }
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkUserNameUnique(user))){


            return Result.error().message("用户名已存在");
        }
        return userService.createUser(user);
    }

    @GetMapping("/hello")
    public String hello(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return "Hello," + authentication.getName() + "!";
    }

    @GetMapping("/error")
    public String error(){


        return "403 error";
    }
}
