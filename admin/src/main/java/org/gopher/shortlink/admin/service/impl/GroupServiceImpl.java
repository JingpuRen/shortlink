package org.gopher.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import groovy.util.logging.Slf4j;
import org.gopher.shortlink.admin.common.convention.exception.ClientException;
import org.gopher.shortlink.admin.dao.entity.GroupDO;
import org.gopher.shortlink.admin.dao.mapper.GroupMapper;
import org.gopher.shortlink.admin.service.GroupService;
import org.gopher.shortlink.admin.util.RandomCodeGenerator;
import org.springframework.stereotype.Service;

/**
 * 短链接分组接口实现层
 */
@Service
@Slf4j
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {
    /**
     * 创建新的短链接分组
     */
    public void createNewGroup(String name){
        // 判断当前的groupName是否已经创建过
        LambdaQueryWrapper<GroupDO> groupDOLambdaQueryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                // todo 这里必须是查当前用户下的，不同的用户是可以创建相同的分组名称的
                .isNull(GroupDO::getUsername)
                .eq(GroupDO::getName, name);

        GroupDO groupDO1 = baseMapper.selectOne(groupDOLambdaQueryWrapper);
        if(groupDO1 != null){
            // 不等于null 说明这个分组名已经存在了，不能创建相同的分组名
            throw new ClientException("分组名重复");
        }

        String gid;
        do {
            // 随机生成六位数的gid
            gid = RandomCodeGenerator.generate6DigitRandomCode();
        } while (hasGid(gid));

        // 创建要插入的实体对象
        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .name(name)
                .build();

        // 插入新的短链接分组对象
        baseMapper.insert(groupDO);
    }

    /**
     * 判断当前用户下生成的这个gid有没有被使用过
     * @return 使用过返回 true ; 没有使用过返回false
     */
    public boolean hasGid(String gid){
        // 判断当前用户名下是否使用过该gid
        LambdaQueryWrapper<GroupDO> groupDOLambdaQueryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                // todo 获取当前线程的用户
                .eq(GroupDO::getUsername, null);
        return baseMapper.selectOne(groupDOLambdaQueryWrapper) != null;
    }
}
