package web02.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web02.common.UserConstants;
import web02.config.Result;
import web02.entity.Role;
import web02.entity.User;
import web02.entity.UserRole;
import web02.mapper.RoleMapper;
import web02.mapper.UserMapper;
import web02.mapper.UserRoleMapper;

import java.util.Set;
import java.util.stream.Stream;

/**
 * @author dkw
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {



    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByName(String userName) {


        return userMapper.queryUserByUsername(userName);
    }

    public String checkPhoneUnique(User user) {


        Long userId = ObjectUtil.isEmpty(user.getId()) ? -1: user.getId();
        User info = userMapper.checkPhoneUnique(user.getMobile());
        if (ObjectUtil.isNotEmpty(info) && !info.getId().equals(userId))
        {


            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    public String checkUserNameUnique(User user) {


        Long userId = ObjectUtil.isEmpty(user.getId()) ? -1: user.getId();
        User info = userMapper.checkUsernameUnique(user.getUsername());
        if (ObjectUtil.isNotEmpty(info) && !info.getId().equals(userId))
        {


            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    public Result createUser(User user) {


        Set<Role> roles = user.getRoles();
        if(CollUtil.isNotEmpty(roles)){


            String passwordNotEncode = user.getPassword();
            String passwordEncode = passwordEncoder.encode(passwordNotEncode);
            user.setPassword(passwordEncode);
            userMapper.insert(user);
            Stream<Long> roleIds = roles.stream().map(Role::getId);
            roleIds.forEach(roleId->{


                Role role = roleMapper.selectById(roleId);
                if(role != null){


                    Long userId = user.getId();
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    userRoleMapper.insert(userRole);
                }
            });
            return Result.ok().message("添加成功");
        }

        return Result.error().message("添加失败");
    }
}
