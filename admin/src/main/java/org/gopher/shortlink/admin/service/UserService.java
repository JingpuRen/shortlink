package org.gopher.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.gopher.shortlink.admin.dao.entity.UserDO;
import org.gopher.shortlink.admin.dto.req.UserLoginReqDTO;
import org.gopher.shortlink.admin.dto.req.UserRegisterReqDTO;
import org.gopher.shortlink.admin.dto.req.UserUpdateReqDTO;
import org.gopher.shortlink.admin.dto.resp.UserLoginRespDTO;
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

    /**
     * 查询用户姓名是否存在
     * @param username
     * @return 存在返回 true ; 不存在返回 false
     */
    Boolean hasUserName(String username);

    /**
     * 用户注册
     * @param userRegisterReqDTO
     */
    void register(UserRegisterReqDTO userRegisterReqDTO);

    /**
     * 更改用户信息
     * @param userUpdateReqDTO
     */
    void updateUser(UserUpdateReqDTO userUpdateReqDTO);

    /**
     * 用户登录
     * @param userLoginReqDTO
     * @return
     */
    UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO);

    /**
     * 检查用户是否登录
     * @param username
     * @param token
     * @return
     */
    Boolean checkLogin(String username,String token);
}
