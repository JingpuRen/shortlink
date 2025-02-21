package org.gopher.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.gopher.shortlink.admin.dao.entity.UserDO;
import org.gopher.shortlink.admin.dto.resp.UserRespDTO;

/**
 * 用户接口层
 */
public interface UserService extends IService<UserDO> {
    /**
     * 根据用户姓名查询用户信息
     * @param username
     * @return
     */
    UserRespDTO getUserByUsername(String username);
}
