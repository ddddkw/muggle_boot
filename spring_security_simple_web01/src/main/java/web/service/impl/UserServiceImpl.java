package web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import web.entity.Authority;
import web.entity.User;
import web.form.CreateUserForm;
import web.service.MybatisUserDetailsService;
import web.service.UserService;

import java.util.Collection;
import java.util.HashSet;
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
        Set<Authority> set = new HashSet<>((Collection<? extends Authority>) user.getAuthorities());
        userInfo.setAuthorities(set);
        userInfo.setEmail(user.getEmail());
        userInfo.setMobile(user.getMobile());
        mybatisUserDetailsService.save(userInfo);
        return true;
    };
}
