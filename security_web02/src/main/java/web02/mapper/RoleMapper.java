package web02.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import web02.entity.Role;

import java.util.List;

/**
 * @author dkw
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {


    List<Role> queryAllRoleByRoleName();
    /**
     * 根据用户名获取角色
     */
    List<Role> loadRolesByUsername(@Param("username") String username);

}
