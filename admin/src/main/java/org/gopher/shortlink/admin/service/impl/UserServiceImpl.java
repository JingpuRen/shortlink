package org.gopher.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.gopher.shortlink.admin.dao.entity.UserDO;
import org.gopher.shortlink.admin.dao.mapper.UserMapper;
import org.gopher.shortlink.admin.dto.resp.UserRespDTO;
import org.gopher.shortlink.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

    /**
     * 根据用户姓名查询用户信息
     * @param username
     * @return
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
     * @param username
     * @return
     */
    @Override
    public Boolean hasUserName(String username) {
//       LambdaQueryWrapper<UserDO> queryWrapper =  Wrappers.lambdaQuery(UserDO.class)
//               .eq(UserDO::getUsername,username);
//       UserDO userDO = baseMapper.selectOne(queryWrapper);

       return userRegisterCachePenetrationBloomFilter.contains(username);
    }


}
