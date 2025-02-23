package org.gopher.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.gopher.shortlink.admin.dao.entity.GroupDO;

/**
 * 短链接分组接口层
 */
public interface GroupService extends IService<GroupDO> {
    /**
     * 创建新的短链接分组
     * @param groupName
     */
    void createNewGroup(String groupName);
}
