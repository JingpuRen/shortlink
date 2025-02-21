package org.gopher.shortlink.admin.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.gopher.shortlink.admin.dao.entity.UserDO;

/**
 * 用户持久层mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

}
