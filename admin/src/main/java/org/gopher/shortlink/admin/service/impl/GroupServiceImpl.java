package org.gopher.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import groovy.util.logging.Slf4j;
import org.gopher.shortlink.admin.common.convention.exception.ClientException;
import org.gopher.shortlink.admin.context.UserContext;
import org.gopher.shortlink.admin.dao.entity.GroupDO;
import org.gopher.shortlink.admin.dao.mapper.GroupMapper;
import org.gopher.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import org.gopher.shortlink.admin.dto.resp.ShortLinkGroupQueryRespDTO;
import org.gopher.shortlink.admin.service.GroupService;
import org.gopher.shortlink.admin.util.RandomCodeGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短链接分组接口实现层
 */
@Service
@Slf4j
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {
    /**
     * 创建新的短链接分组的重载方法，用于刚刚创建用户时创建默认分组
     * @param name 分组名称
     * @param username 当前登录的用户姓名或者创建时候的用户姓名
     */
    public void createNewGroup(String username,String name){
        String gid;
        do {
            // 随机生成六位数的gid
            gid = RandomCodeGenerator.generate6DigitRandomCode();
        } while (hasGid(username,gid));

        // 创建要插入的实体对象
        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .sortOrder(0) // 默认生成的分组排序是0
                .name(name)
                .username(username)
                .build();

        // 插入新的短链接分组对象
        baseMapper.insert(groupDO);
    }

    /**
     * 创建新的短链接分组
     */
    @Override
    public void createNewGroup(String name){
        // 判断当前的groupName是否已经创建过
        LambdaQueryWrapper<GroupDO> groupDOLambdaQueryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                // todo 从当前线程获取用户的信息
                .eq(GroupDO::getUsername, UserContext.getUsername())
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
                .sortOrder(0) // 默认敢不敢生成的分组排序是0
                .name(name)
                .username(UserContext.getUsername())
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
                .eq(GroupDO::getUsername,UserContext.getUsername());
        return baseMapper.selectOne(groupDOLambdaQueryWrapper) != null;
    }

    /**
     * 判断注册用户名下是否使用过该生成的gid
     * @return 使用过返回 true ; 没有使用过返回false
     */
    public boolean hasGid(String username,String gid){
        // 判断注册用户名下是否使用过该gid
        LambdaQueryWrapper<GroupDO> groupDOLambdaQueryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername,username);
        return baseMapper.selectOne(groupDOLambdaQueryWrapper) != null;
    }

    /**
     * 查询当前用户用户下的短链接分组，其实是有参数的，参数就是隐形的用户
     */
    @Override
    public List<ShortLinkGroupQueryRespDTO> queryGroup(){
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag,0)
                // todo 获取当前线程下的用户
                .eq(GroupDO::getUsername,UserContext.getUsername())
                .orderByDesc(GroupDO::getSortOrder,GroupDO::getUpdateTime);

        List<GroupDO> selectedList = baseMapper.selectList(queryWrapper);

        List<ShortLinkGroupQueryRespDTO> resultList = selectedList.stream()
                .map(each -> BeanUtil.toBean(each, ShortLinkGroupQueryRespDTO.class))
                .toList();

        return resultList;
    }

    /**
     * 修改短链接分组名称
     */
    @Override
    public void updateGroup(ShortLinkGroupUpdateReqDTO shortLinkGroupUpdateReqDTO) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, shortLinkGroupUpdateReqDTO.getGid());
        GroupDO groupDO = GroupDO.builder().name(shortLinkGroupUpdateReqDTO.getName()).build();
        baseMapper.update(groupDO,queryWrapper);
    }

    /**
     * 删除短链接分组
     */
    @Override
    public void deleteGroup(String gid){
        // 删除一般都是软删除，因此我们一般是用update去做
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, gid);

        // tip update的步骤是先创建好条件查询器wrapper，创建一个新的实体，在实体中将要修改的字段进行赋值
        // tip 然后我们再调用basemapper.update就可以了
        GroupDO groupDO = GroupDO.builder()
                .delFlag(1).build();
        baseMapper.update(groupDO,queryWrapper);
    }
}
