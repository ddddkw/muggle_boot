package web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import web.entity.User;

import java.util.List;

/**
 * @author dkw
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> queryUserByUsername(String username);
}