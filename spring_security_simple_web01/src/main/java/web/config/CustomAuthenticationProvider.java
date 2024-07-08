package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import web.service.MybatisUserDetailsService;

import java.util.Arrays;

/**
 * @author dkw
 * AuthenticationProvider实现了身份验证逻辑并且委托给UserDetailsService和PasswordEncoder以便进行用户和密码管理
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MybatisUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 进行用户身份验证逻辑，在这里加上用户校验逻辑后，通过userDetailsService中的loadUserByUsername获取用户信息
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        //getName()方法被Authentication从Principal接口处继承
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        //这个条件通常会调用UserDetailsService和PasswordEncoder用来调试用户名和密码
        UserDetails u = userDetailsService.loadUserByUsername(username);
        System.out.println(password);
        System.out.println(u.getPassword());
        // 这里的passwordEncoder.matches匹配的是输入的原密码和数据库中的加密后的密码，id就是加密后的密码的前面的加密类型，根据id匹配对应的加解密解析器
        if (passwordEncoder.matches(password, u.getPassword())) {

            //如果密码匹配，则返回Authentication接口的实现以及必要的详细信息
            return new UsernamePasswordAuthenticationToken(username, password, u.getAuthorities());
        } else {

            //密码不匹配，抛出异常
            throw new BadCredentialsException("Something went wrong!");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationType) {


        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }
}