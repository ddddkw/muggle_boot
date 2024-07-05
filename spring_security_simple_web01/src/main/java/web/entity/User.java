package web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Set;

/**
 * @author dkw
 */
@TableName("users")
@Data
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
    @TableField("enabled")
    private Boolean enabled;

    // 获取用户对应的权限信息
    @TableField(exist = false)
    private Set<Authority> authorities;

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


        return this.enabled;
    }

}