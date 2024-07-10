package web02.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import web02.entity.Authority;

import java.util.List;
import java.util.Set;

/**
 * @author dkw
 */
@Mapper
public interface AuthorityMapper extends BaseMapper<Authority> {


    /**
     * 通过角色名称list查询菜单权限
     */
    List<Authority> loadPermissionByRoleCode(@Param("roleInfos") Set<String> roleInfos);
}
