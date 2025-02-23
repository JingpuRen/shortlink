package org.gopher.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gopher.shortlink.admin.dao.entity.GroupDO;
import org.gopher.shortlink.admin.dao.mapper.GroupMapper;
import org.gopher.shortlink.admin.service.GroupService;

/**
 * 短链接分组接口实现层
 */
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {
}
