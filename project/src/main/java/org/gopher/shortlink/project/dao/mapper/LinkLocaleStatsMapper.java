package org.gopher.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gopher.shortlink.project.dao.entity.LinkLocaleStatsDO;

/**
 * 地区统计访问实体层
 */
@Mapper
public interface LinkLocaleStatsMapper extends BaseMapper<LinkLocaleStatsDO> {
    // tip : 记得调这个时候是调shortLinkLocaleStats而不是调insert
    @Insert("INSERT INTO t_link_locale_stats(full_short_url,gid,date,cnt,province,city,adcode,country,create_time,update_time,del_flag) " +
            "VALUES (#{linkLocaleStats.fullShortUrl},#{linkLocaleStats.gid},#{linkLocaleStats.date},#{linkLocaleStats.cnt},#{linkLocaleStats.province},#{linkLocaleStats.city},#{linkLocaleStats.adcode},#{linkLocaleStats.country},NOW(),NOW(),0) ON DUPLICATE KEY UPDATE " +
            "cnt = cnt + #{linkLocaleStats.cnt};")
    void shortLinkLocaleStats(@Param("linkLocaleStats") LinkLocaleStatsDO linkLocaleStatsDO);
}
