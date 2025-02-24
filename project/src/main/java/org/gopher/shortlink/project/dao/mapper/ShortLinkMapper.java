package org.gopher.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.gopher.shortlink.project.dao.entity.ShortLinkDO;

@Mapper
public interface ShortLinkMapper extends BaseMapper<ShortLinkDO> {

}
