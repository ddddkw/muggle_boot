package web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dkw
 */
@TableName("user_authority")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthority {


    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long userId;
    private Long authorityId;
}