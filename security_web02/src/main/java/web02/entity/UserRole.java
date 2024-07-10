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
@TableName("user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {


    @TableId(type= IdType.ASSIGN_ID)
    private Long id;
    @TableField("userId")
    private Long userId;
    @TableField("roleId")
    private Long roleId;
}
