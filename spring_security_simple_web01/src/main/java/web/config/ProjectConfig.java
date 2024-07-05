package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import web.encodeMethods.PlainTextPasswordEncoder;
import web.encodeMethods.Sha512PasswordEncoder;
import web.service.MybatisUserDetailsService;

import java.util.HashMap;

/**
 * @author dkw
 * 重写userDetailsService和配置过滤链也可以分开写，都用@Configuration注解配置一下即可
 */
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MybatisUserDetailsService userDetailsService;

    @Autowired
    private PlainTextPasswordEncoder passwordEncoder;

    @Autowired
    private Sha512PasswordEncoder sha512PasswordEncoder;

    //    @Autowired
    //    private CustomAuthenticationProvider authenticationProvider;

    // 重写端点授权配置，配置过滤链
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/create").permitAll()
                .antMatchers("/muggle/user/create").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().disable(); //所有请求都需要身份验证
    }

    // 先进行过滤链，然后经过authenticationProvider对用户身份验证，重写authenticationProvider中对用户验证的逻辑
    @Override
    protected void configure(AuthenticationManagerBuilder  auth) throws Exception {
        // 在校验用户身份时，对authenticationProvider进行重写，authenticationProvider中需要用到UserDetailsService获取用户详情，同时需要使用PasswordEncoder验证密码
        // auth.authenticationProvider(authenticationProvider); // authenticationProvider是进行用户身份验证时如何进行验证
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    // 重写PasswordEncoder方法
    @Bean
    public PasswordEncoder passwordEncoder() {
        HashMap<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        // DelegatingPasswordEncoder会根据idForEncode匹配对应的方式
        // DelegatingPasswordEncoder具有一个它可以委托的PasswordEncoder实现的列表。DelegatingPasswordEncoder会将每一个实例存储在一个映射中。
        return new DelegatingPasswordEncoder("bcrypt",encoders);
    }
}