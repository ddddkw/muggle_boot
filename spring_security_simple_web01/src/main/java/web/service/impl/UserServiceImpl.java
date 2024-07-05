package web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.entity.Authority;
import web.entity.User;
import web.form.CreateUserForm;
import web.service.MybatisUserDetailsService;
import web.service.UserService;

import java.util.Set;

/**
 * @author dkw
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MybatisUserDetailsService mybatisUserDetailsService;

    @Override
    public Boolean createUser(CreateUserForm user){
        User userInfo = new User();
        userInfo.setEnabled(user.getEnabled());
        userInfo.setUsername(user.getUsername());
        userInfo.setPassword(user.getPassword());
        userInfo.setAuthorities((Set<Authority>) user.getAuthorities());
        userInfo.setEmail(user.getEmail());
        userInfo.setMobile(user.getMobile());
        mybatisUserDetailsService.save(userInfo);
        return true;
    };
}
