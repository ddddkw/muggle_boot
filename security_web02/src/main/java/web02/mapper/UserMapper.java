package web02.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import web02.entity.User;

/**
 * @author dkw
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User queryUserByUsername(String username);

    User checkUsernameUnique(String userName);

    User checkPhoneUnique(String phone);
}
