package web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.entity.User;
import web.mapper.UserMapper;
import web.service.UserDetailsService;

import java.util.List;


/**
 * @author dkw */
@Service
public class MybatisUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        // 调用mapper.xml中的sql查询对应的数据
        List<User> users = userMapper.queryUserByUsername(username);
        return users.stream().findFirst().orElseThrow(()->new UsernameNotFoundException("User Not Found"));
    }
}