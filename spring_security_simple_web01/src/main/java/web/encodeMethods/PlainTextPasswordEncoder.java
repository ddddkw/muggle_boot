package web.encodeMethods;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PlainTextPasswordEncoder implements PasswordEncoder {


    @Override
    public String encode(CharSequence rawPassword) {


        //并没有变更密码，而是原样返回
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {


        //检查两个字符是否相等
        return rawPassword.equals(encodedPassword);
    }
}
