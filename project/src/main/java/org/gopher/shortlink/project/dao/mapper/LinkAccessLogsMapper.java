package org.gopher.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.gopher.shortlink.project.dao.entity.LinkAccessLogsDO;

/**
 * 短链接高频IP统计持久层
 */
@Mapper
public interface LinkAccessLogsMapper extends BaseMapper<LinkAccessLogsDO> {

}
