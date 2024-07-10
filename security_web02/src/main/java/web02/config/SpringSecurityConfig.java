package web02.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import web02.securityFilters.AuthenticationLoggingFilter;
import web02.securityFilters.RequestValidationFilter;
import web02.securityUtils.CommonLoginFailureHandler;
import web02.securityUtils.CommonLoginSuccessHandler;
import web02.service.Impl.UserDetailsServiceImpl;

import java.util.HashMap;

/**
 * @author Administrator
 */
@Configuration
@RequiredArgsConstructor
@EnableAsync
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserDetailsServiceImpl commonUserDetailServiceImpl;
    private final CommonLoginSuccessHandler successHandler;
    private final CommonLoginFailureHandler failureHandler;
    private final RequestValidationFilter requestValidationFilter;
    private final AuthenticationLoggingFilter authenticationLoggingFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilterBefore(requestValidationFilter, BasicAuthenticationFilter.class) // 在BasicAuthenticationFilter过滤器之前加一个自定义过滤器requestValidationFilter
                .addFilterAfter(authenticationLoggingFilter,BasicAuthenticationFilter.class)
                .csrf().disable()
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers("/home","/toLogin","/login", "/user/create", "/kaptcha", "/smsCode", "/smslogin").permitAll()
                .anyRequest().hasRole("USER");

        // denyAll()拒绝所有请求
//        http
//                .csrf().disable()
//                .formLogin()
//                .successHandler(successHandler)
//                .failureHandler(failureHandler)
//                .and()
//                .httpBasic().and()
//                .authorizeRequests()
//                .antMatchers("/home","/toLogin","/login", "/user/create", "/kaptcha", "/smsCode", "/smslogin").permitAll()
//                .anyRequest().authenticated(); // 除了放行外的所有接口都进行身份验证
//                .anyRequest().hasAnyAuthority("read","write"); // 用权限名称进行匹配
                // 让post的a请求需要通过认证，让post的b请求不需要通过认证，
//                .mvcMatchers(HttpMethod.POST,"/hello/a").authenticated()
//                .mvcMatchers(HttpMethod.GET,"/hello/a").permitAll();
                // 实现只有特定的角色才能调用特定的接口
//              .mvcMatchers("/hello").hasRole("ADMIN")
//              .mvcMatchers("/xiao").hasRole("USER");
//              .anyRequest().hasAuthority("ROLE_USER"); // 用角色名称进行匹配，与下一行使用的是相同的功能
//              .anyRequest().hasRole("USER"); // 用角色名称进行匹配
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 使用重写UserDetailsService进行验证，这里会将用户信息，角色信息和权限信息存储在上下文中，验证通过以后会进入过滤链中继续往下走
        auth.userDetailsService(commonUserDetailServiceImpl)
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {


        HashMap<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }
}
