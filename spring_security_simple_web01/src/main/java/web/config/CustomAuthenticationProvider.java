package web.config;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author dkw
 * AuthenticationProvider实现了身份验证逻辑并且委托给UserDetailsService和PasswordEncoder以便进行用户和密码管理
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    // 进行用户身份验证逻辑，在这里加上用户校验逻辑后，就无需再对userDetailsService进行重写，直接对输入的用户和密码验证即可
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        //getName()方法被Authentication从Principal接口处继承
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        //这个条件通常会调用UserDetailsService和PasswordEncoder用来调试用户名和密码
        if("test".equals(username) && "123456".equals(password)){


            return new UsernamePasswordAuthenticationToken(username,password, Arrays.asList());
        }else {


            throw new AuthenticationCredentialsNotFoundException("Error in authentication!");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationType) {


        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationType);
    }
}