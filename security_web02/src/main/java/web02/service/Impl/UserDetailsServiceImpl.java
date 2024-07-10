package web02.service.Impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web02.JwtUtils.JwtUserDto;
import web02.entity.Authority;
import web02.entity.Role;
import web02.entity.User;
import web02.mapper.AuthorityMapper;
import web02.mapper.UserMapper;
import web02.service.RoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dkw
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public JwtUserDto loadUserByUsername(String username) throws UsernameNotFoundException {


        // 根据用户名获取用户,为避免循环依赖，这里的userService.getUserByName方法可以直接换为使用mapper实现
        // 根据用户名称获取到用户信息
        User user = userMapper.queryUserByUsername(username);
        if (user == null ){
            throw new BadCredentialsException("用户名或密码错误");
        }
        // 根据用户名获取到用户的权限列表
        List<Role> roles = roleService.loadRolesByUsername(username);
        // 将角色名称收集为set集合
        Set<String> roleInfos = roles.stream().map(Role::getRoleName).collect(Collectors.toSet());
        // 通过角色名称list查询菜单权限
        List<Authority> authorities = authorityMapper.loadPermissionByRoleCode(roleInfos);
        // 将角色的权限名称收集为list
        List<String> authorityNames = authorities.stream().map(Authority::getAuthorityName).filter(StrUtil::isNotEmpty).collect(Collectors.toList());
        authorityNames.addAll(roleInfos.stream().map(roleName->"ROLE_"+roleName).collect(Collectors.toList()));
        // 返回jwt工具将用户信息，角色信息和权限信息进行存储在上下文中
        return new JwtUserDto(user, new HashSet<>(roles), authorityNames);
    }
}
