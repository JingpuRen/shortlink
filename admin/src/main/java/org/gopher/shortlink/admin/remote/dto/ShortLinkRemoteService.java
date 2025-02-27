package org.gopher.shortlink.admin.remote.dto;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.gopher.shortlink.admin.common.convention.result.Result;
import org.gopher.shortlink.admin.remote.dto.req.RecycleBinSaveReqDTO;
import org.gopher.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import org.gopher.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import org.gopher.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import org.gopher.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import org.gopher.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * 短链接中台远程调用服务
 */
public interface ShortLinkRemoteService {

    /**
     * 创建短链接
     */
    default Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO){
        String post = HttpUtil.post("http://127.0.0.1:8001/api/short-link/project/v1/create", JSON.toJSONString(shortLinkCreateReqDTO));
        return JSON.parseObject(post, new TypeReference<>() {
        });
    }

    /**
     * 短链接中台分页查询
     */
    default Result<IPage<ShortLinkPageRespDTO>> PageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("gid",shortLinkPageReqDTO.getGid());
        requestMap.put("current",shortLinkPageReqDTO.getCurrent());
        requestMap.put("size",shortLinkPageReqDTO.getSize());

        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/project/v1/page", requestMap);

        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }

    /**
     * 短链接信息修改
     */
    default void updateShortLinkInfo(ShortLinkUpdateReqDTO shortLinkUpdateReqDTO){
        String post = HttpUtil.post("http://127.0.0.1:8001/api/short-link/project/v1/update", JSON.toJSONString(shortLinkUpdateReqDTO));
    }

    /**
     * 将短链接移到回收站
     */
    default void saveRecycleBin(RecycleBinSaveReqDTO recycleBinSaveReqDTO) {
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/recycle/v1/save", JSON.toJSONString(recycleBinSaveReqDTO));
    }
}
