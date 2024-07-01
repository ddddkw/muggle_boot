package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author dkw
 * 重写userDetailsService和配置过滤链也可以分开写，都用@Configuration注解配置一下即可
 */
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    // 重写端点授权配置，配置过滤链
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .anyRequest().authenticated(); //所有请求都需要身份验证
    }

    // 先进行过滤链，然后经过authenticationProvider对用户身份验证
    @Override
    protected void configure(AuthenticationManagerBuilder  auth) throws Exception {
        auth.authenticationProvider(authenticationProvider); // authenticationProvider是进行用户身份验证时如何进行验证
    }

//    @Bean
//    public void userDetailsService(AuthenticationManagerBuilder auth) throws Exception {
//
//        //声明UserDetailsService,以便将用户存储在内存中，声明userDetailsService，用户信息会自动存储在内存中
//        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//        // 创建一个用户“test”，并设置密码和角色
//        UserDetails user = User.withUsername("test")
//                .password("123456")
//                .authorities("admin")
//                .build();
//        //添加该用户以便让UserDetailsService对其进行管理
//        userDetailsService.createUser(user);
//        auth.userDetailsService(userDetailsService) // 应该使用哪个UserDetailsService实例来加载用户信息
//                .passwordEncoder(NoOpPasswordEncoder.getInstance()); //配置了Spring Security使用的密码编码器
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){


        return NoOpPasswordEncoder.getInstance();
    }
}