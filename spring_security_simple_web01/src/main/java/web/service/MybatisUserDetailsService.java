package web.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.entity.Authority;
import web.entity.User;
import web.entity.UserAuthority;
import web.mapper.AuthorityMapper;
import web.mapper.UserAuthorityMapper;
import web.mapper.UserMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MybatisUserDetailsService extends ServiceImpl<UserMapper, User> implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private UserAuthorityMapper userAuthorityMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        List<User> users = userMapper.queryUserByUsername(username);
        System.out.println(users);
        return users.stream().findFirst().orElseThrow(()->new UsernameNotFoundException("User Not Found"));
    }

    @Override
    @Transactional
    public boolean save(User user) {
        try {
            String passwordNotEncode = user.getPassword();
            String passwordEncoded = passwordEncoder.encode(passwordNotEncode);
            user.setPassword(passwordEncoded);
            userMapper.insert(user);
            // authorities是用户的权限信息
            Set<Authority> authorities = user.getAuthorities();
            // 将用户的权限id收集起来
            Set<Long> authorityIds = authorities.stream().map(Authority::getId).collect(Collectors.toSet());
            authorityIds.forEach(id -> {
                // 根据id获取对应的权限详细信息
                Authority authority = authorityMapper.selectById(id);
                // 如果用户权限信息不为空，将权限和用户信息存到用户-权限关联表中
                if(authority != null){
                    Long userId = user.getId();
                    UserAuthority userAuthority = new UserAuthority();
                    userAuthority.setUserId(userId);
                    userAuthority.setAuthorityId(id);
                    userAuthorityMapper.insert(userAuthority);
                }
            });
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }

}