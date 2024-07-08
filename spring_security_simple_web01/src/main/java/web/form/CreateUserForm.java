package web.form;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import web.entity.Authority;

import java.util.List;

/**
 * @author dkw
 */
@Data
public class CreateUserForm {


    private String username;

    private String mobile;

    private String password;

    private String email;

    private Boolean enabled;

    private List<Authority> authorities;
}
