package org.gopher.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.gopher.shortlink.admin.common.convention.exception.ClientException;
import org.gopher.shortlink.admin.dao.entity.UserDO;
import org.gopher.shortlink.admin.dao.mapper.UserMapper;
import org.gopher.shortlink.admin.dto.req.UserLoginReqDTO;
import org.gopher.shortlink.admin.dto.req.UserRegisterReqDTO;
import org.gopher.shortlink.admin.dto.req.UserUpdateReqDTO;
import org.gopher.shortlink.admin.dto.resp.UserLoginRespDTO;
import org.gopher.shortlink.admin.dto.resp.UserRespDTO;
import org.gopher.shortlink.admin.service.GroupService;
import org.gopher.shortlink.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.gopher.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    // 引入布隆过滤器
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

    // 引入redisson客户端
    private final RedissonClient redissonClient;

    // 引入redis
    private final StringRedisTemplate stringRedisTemplate;

    // 引入groupService
    private final GroupService groupService;

    /**
     * 根据用户姓名查询用户信息
     */
    @Override
    public UserRespDTO getUserByUsername(String username) {
        // Mybatis-plus查询
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername,username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        // 将UserDO类型的对象转化成UserRespDTO类型的对象
        UserRespDTO userRespDTO = new UserRespDTO();
        BeanUtils.copyProperties(userDO,userRespDTO);
        return userRespDTO;
    }

    /**
     * 查询用户姓名是否存在
     */
    @Override
    public Boolean hasUserName(String username) {
//       LambdaQueryWrapper<UserDO> queryWrapper =  Wrappers.lambdaQuery(UserDO.class)
//               .eq(UserDO::getUsername,username);
//       UserDO userDO = baseMapper.selectOne(queryWrapper);

       return userRegisterCachePenetrationBloomFilter.contains(username);
    }

    /**
     * 新增用户
     */
    public void register(UserRegisterReqDTO userRegisterReqDTO){
        if(hasUserName(userRegisterReqDTO.getUsername())){
            throw new ClientException("用户名已经存在");
        }

        // 对要注册的用户名上锁！！！！
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + userRegisterReqDTO.getUsername());

        // 通过分布式锁机制防止大量请求
        try{
            if(lock.tryLock()){
                int inserted = baseMapper.insert(BeanUtil.toBean(userRegisterReqDTO, UserDO.class));
                if(inserted > 1){
                    throw new ClientException("用户新增失败");
                }
                // 到这里说明用户新增成功，加入到数据库后，我们将其加入到布隆过滤器中
                userRegisterCachePenetrationBloomFilter.add(userRegisterReqDTO.getUsername());
                // 为注册的用户创建默认的短链接分组
                groupService.createNewGroup(userRegisterReqDTO.getUsername(),"默认分组");
                return;
            }
            // 如果没有获取到锁，说明锁已经被别人使用，那么这个名字就被别人注册掉了
            throw new ClientException("用户名已被注册");
        }finally {
            lock.unlock();
        }
    }

    /**
     * 更新用户信息
     */
    public void updateUser(UserUpdateReqDTO userUpdateReqDTO){
        // todo 验证当前用户名是否是修改用户
        LambdaUpdateWrapper<UserDO> lambdaUpdateWrapper = Wrappers.lambdaUpdate(UserDO.class)
                .eq(UserDO::getUsername, userUpdateReqDTO.getUsername());
        baseMapper.update(BeanUtil.toBean(userUpdateReqDTO,UserDO.class),lambdaUpdateWrapper);
    }

    /**
     * 用户登录
     */
    public UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO){
        // 1. 判断登录用户是否存在
        LambdaQueryWrapper<UserDO> userLoginReqLambdaQuery = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, userLoginReqDTO.getUsername())
                .eq(UserDO::getPassword, userLoginReqDTO.getPassword())
                .eq(UserDO::getDelFlag,0);
        UserDO userDO = baseMapper.selectOne(userLoginReqLambdaQuery);
        if(userDO == null){
            throw new ClientException("用户不存在");
        }

        // 判断用户是否已经登录，防止用户重复登录
        if(Boolean.TRUE.equals(stringRedisTemplate.hasKey("login:" + userLoginReqDTO.getUsername()))){
            throw new ClientException("用户已经登录");
        }

        // 到此处说明登录用户是存在的并且还没有登陆过
        // 生成uuid作为token
        String token = UUID.randomUUID().toString();

        /**
         * 哈希存储
         * key : login_:{username}
         * hash:
         *  key : token
         *  value : JSON字符串格式的用户信息
         */
        stringRedisTemplate.opsForHash().put("login:" + userLoginReqDTO.getUsername(),token, JSON.toJSONString(userDO));
        stringRedisTemplate.expire("login:" + userLoginReqDTO.getUsername(),30L,TimeUnit.DAYS);

        // 返回token
        UserLoginRespDTO userLoginRespDTO = new UserLoginRespDTO();
        userLoginRespDTO.setToken(token);

        return userLoginRespDTO;
    }

    /**
     * 检查用户是否登录
     */
    public Boolean checkLogin(String username,String token){
        return stringRedisTemplate.opsForHash().get("login:" + username,token) != null;
    }
}
