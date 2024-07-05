package web.Interface;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author dkw
 * UserDetails重写两个方法？
 */
public interface UserDetails extends Serializable {


    /**
     * 将应用程序用户的权限返回成一个GrantedAuthority实例集合
     */
    Collection<? extends GrantedAuthority> getAuthorities();

    /**
     * 下面2个方法会返回用户凭据
     */
    String getPassword();
    String getUsername();

    /**
     * 出于不同的原因，这4个方法会启用或禁用账户
     */
    boolean isAccountNonExpired();
    boolean isAccountNonLocked();
    boolean isCredentialsNonExpired();
    boolean isEnabled();
}
