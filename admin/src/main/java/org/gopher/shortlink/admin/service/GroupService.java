package org.gopher.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.gopher.shortlink.admin.dao.entity.GroupDO;
import org.gopher.shortlink.admin.dto.resp.ShortLinkGroupQueryRespDTO;

import java.util.List;

/**
 * 短链接分组接口层
 */
public interface GroupService extends IService<GroupDO> {
    /**
     * 创建新的短链接分组
     */
    void createNewGroup(String groupName);

    /**
     * 查询短链接分组
     */
    List<ShortLinkGroupQueryRespDTO> queryGroup();
}
