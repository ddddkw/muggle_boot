package web02.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * @author dkw
 */
@TableName("users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {


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
    @TableField(exist = false)
    private Set<Role> roles;
    /**
     * 图片验证码
     */
    @TableField(exist = false)
    private String captcha;

    /**
     * uuid
     */
    @TableField(exist = false)
    private String uuid;
}
