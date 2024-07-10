package web02.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web02.entity.Authority;
import web02.entity.Role;
import web02.entity.RoleAuthority;
import web02.mapper.AuthorityMapper;
import web02.mapper.RoleAuthorityMapper;
import web02.mapper.RoleMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author dkw
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {


    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    public List<Role> queryAllRoleByRoleName(){


        return roleMapper.queryAllRoleByRoleName();
    }

    public void saveRole(Role role){


        Set<Authority> authorities = role.getAuthorities();
        if(CollUtil.isNotEmpty(authorities)){


            Stream<Long> authorityIds = authorities.stream().map(Authority::getId);
            roleMapper.insert(role);
            authorityIds.forEach(authorityId->{


                Authority authority = authorityMapper.selectById(authorityId);
                if(authority != null){


                    RoleAuthority roleAuthority = new RoleAuthority();
                    roleAuthority.setRoleId(role.getId());
                    roleAuthority.setAuthorityId(authorityId);
                    roleAuthorityMapper.insert(roleAuthority);
                }
            });
        }
    }

    public List<Role> loadRolesByUsername(String username){


        return roleMapper.loadRolesByUsername(username);
    }

}
