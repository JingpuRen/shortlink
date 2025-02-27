package org.gopher.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gopher.shortlink.project.dao.entity.ShortLinkDO;
import org.gopher.shortlink.project.dto.req.RecycleBinRecoverReqDTO;
import org.gopher.shortlink.project.dto.req.RecycleBinRemoveReqDTO;
import org.gopher.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import org.gopher.shortlink.project.dto.req.ShortLinkPageReqDTO;
import org.gopher.shortlink.project.dto.resp.ShortLinkPageRespDTO;

public interface RecycleBinService extends IService<ShortLinkDO> {
    /**
     * 保存回收站
     */
    void saveRecycleBin(RecycleBinSaveReqDTO recycleBinSaveReqDTO);

    /**
     * 回收站内容分页展示
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO);

    /**
     * 恢复短链接
     */
    void recoverShortLink(RecycleBinRecoverReqDTO recycleBinRecoverReqDTO);

    /**
     * 从回收站移除短链接
     */
    void removeShortLinkFromRecycleBin(RecycleBinRemoveReqDTO recycleBinRemoveReqDTO);
}
