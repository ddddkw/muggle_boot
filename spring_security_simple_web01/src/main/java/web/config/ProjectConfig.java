package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Autowired
    private CustomEntryPoint customEntryPoint;

    // 重写端点授权配置，配置过滤链
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/create").permitAll() // 允许未授权访问
                .anyRequest().authenticated()// 所有其他请求需要认证
                .and()
                .formLogin().permitAll() // 允许未授权访问登录页面
                .and()
                .httpBasic()
                .and()
                .csrf().disable() // 使用antMatchers时需要将csrf禁用掉，否则需要填加ignoringAntMatchers进行配置
                ;
        // 启用HTTP Basic认证，可在其中自定义加上一些校验规则，比如校验失败后的规则
        http.httpBasic();
        http.exceptionHandling() // 配置异常处理的部分，它定义了当认证失败或未认证的请求尝试访问受保护资源时应采取的操作
                .authenticationEntryPoint(customEntryPoint) // 指定一个自定义的 AuthenticationEntryPoint，这是当用户尝试访问一个需要认证的资源但尚未认证时，Spring Security 应调用的类
                .and()
                // 对基于表单登录的形式，也有两种情况，认证失败和认证成功的处理handler
                .formLogin() // 表单登录，启动程序后，调用响应接口时，会跳转到一个登录页面，没注册UserDetailsService，就可以使用所提供的默认凭据进行登录
                // successHandler和failureHandler是自己定义的处理函数
//                .successHandler(successHandler)
//                .failureHandler(failureHandler)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
//        http.csrf().ignoringAntMatchers("/your/path/**", "/another/path/**");
    }



    // 先进行过滤链，然后经过authenticationProvider对用户身份验证，重写authenticationProvider中对用户验证的逻辑
    @Override
    protected void configure(AuthenticationManagerBuilder  auth) throws Exception {
        // 在校验用户身份时，对authenticationProvider进行重写，authenticationProvider中需要用到UserDetailsService获取用户详情，同时需要使用PasswordEncoder验证密码
         auth.authenticationProvider(authenticationProvider); // authenticationProvider是进行用户身份验证时如何进行验证
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());
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