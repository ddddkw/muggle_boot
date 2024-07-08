package web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * Represents a user entity in the system.
 * Implements UserDetails for integration with Spring Security.
 *
 * @author dkw
 */
@TableName("users")
//@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable, UserDetails {

    @TableId(type=IdType.ASSIGN_ID)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("mobile")
    private String mobile;

    @TableField("password")
    private String password;

    @TableField("email")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @TableField("enabled")
    private Boolean enabled;

    /**
     * A collection of authorities associated with the user.
     * This field is not persisted in the database.
     */
    @TableField(exist = false)
    private Set<Authority> authorities; // Note: Changed from Authority to GrantedAuthority for clarity

    // UserDetails methods implementation

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    // 是标识账号是否启用的字段？ isEnable相当于getEnable，导致JavaBean里面有两个getEnabled方法，违反了JavaBean的规范，只要将其中一个删掉就可以了
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}