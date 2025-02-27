package org.gopher.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.gopher.shortlink.project.dao.entity.ShortLinkDO;
import org.gopher.shortlink.project.dto.req.RecycleBinSaveReqDTO;

public interface RecycleBinService extends IService<ShortLinkDO> {
    /**
     * 保存回收站
     */
    void saveRecycleBin(RecycleBinSaveReqDTO recycleBinSaveReqDTO);
}
