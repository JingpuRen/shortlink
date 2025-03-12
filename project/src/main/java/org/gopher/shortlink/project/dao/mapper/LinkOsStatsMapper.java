package org.gopher.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gopher.shortlink.project.dao.entity.LinkOsStatsDO;

/**
 * 操作系统统计持久层
 */
@Mapper
public interface LinkOsStatsMapper extends BaseMapper<LinkOsStatsDO> {
    @Insert("INSERT INTO t_link_os_stats(full_short_url,gid,date,cnt,os,create_time,update_time,del_flag) " +
            "VALUES (#{linkOsStats.fullShortUrl},#{linkOsStats.gid},#{linkOsStats.date},#{linkOsStats.cnt},#{linkOsStats.os},NOW(),NOW(),0) ON DUPLICATE KEY UPDATE " +
            "cnt = cnt + #{linkOsStats.cnt};")
    void shortLinkOsStats(@Param("linkOsStats") LinkOsStatsDO linkOsStatsDO);
}
