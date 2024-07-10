package web02.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dkw
 */
@TableName("role_authority")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAuthority {


    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @TableField("roleId")
    private Long roleId;
    @TableField("authorityId")
    private Long authorityId;
}
