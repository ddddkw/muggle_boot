package web02.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author dkw
 */
@TableName("roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {


    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @TableField("roleName")
    private String roleName;
    @TableField(exist = false)
    private Set<Authority> authorities;
}
