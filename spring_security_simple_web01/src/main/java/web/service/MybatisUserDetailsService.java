package web.service;

import com.baomidou.mybatisplus.extension.service.IService;
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

            Set<Authority> authorities = (Set<Authority>) user.getAuthorities();

            authorities.forEach(authority -> {
                if (authority != null) { // 防止 NPE
                    Authority detailedAuthority = authorityMapper.selectById(authority.getId());
                    if (detailedAuthority != null) {
                        Long userId = user.getId();
                        UserAuthority userAuthority = new UserAuthority();
                        userAuthority.setUserId(userId);
                        userAuthority.setAuthorityId(authority.getId());
                        userAuthorityMapper.insert(userAuthority);
                    }
                }
            });

            return true;
        } catch (Exception e) {
            log.error("Error saving user: ", e);
            return false;
        }
    }

}