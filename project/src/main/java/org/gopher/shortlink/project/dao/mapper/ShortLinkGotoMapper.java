package org.gopher.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.gopher.shortlink.project.dao.entity.ShortLinkGotoDO;

/**
 * 短链接跳转持久层
 */
@Mapper
public interface ShortLinkGotoMapper extends BaseMapper<ShortLinkGotoDO> {
}
