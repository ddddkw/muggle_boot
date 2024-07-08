package web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author dkw
 */
@TableName("authorities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {


    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String authorityName;

    @Override
    public String getAuthority() {


        return authorityName;
    }

}