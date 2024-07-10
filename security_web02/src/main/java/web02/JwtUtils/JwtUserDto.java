package web02.JwtUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import web02.entity.Role;
import web02.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author dkw
 */
@Data
@ToString
public class JwtUserDto implements UserDetails {



    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登陆时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;
    private User user;
    private Set<Role> roleInfo;
    /**
     * 用户权限的集合
     */
    @JsonIgnore
    private List<String> authorityNames;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorityNames.forEach(authorityName->authorities.add(new SimpleGrantedAuthority(authorityName)));
        return authorities;
    }

    @Override
    public String getPassword() {


        return user.getPassword();
    }

    @Override
    public String getUsername() {


        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {


        return true;
    }

    @Override
    public boolean isAccountNonLocked() {


        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {


        return true;
    }

    @Override
    public boolean isEnabled() {


        return user.getEnabled();
    }
    public JwtUserDto(User user, Set<Role> roleInfo, List<String> authorityNames) {


        this.user = user;
        this.roleInfo = roleInfo;
        this.authorityNames = authorityNames;
    }
}
