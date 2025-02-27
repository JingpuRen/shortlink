package org.gopher.shortlink.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gopher.shortlink.project.dao.entity.LinkAccessStatsDO;
import org.gopher.shortlink.project.dao.mapper.LinkAccessStatsMapper;
import org.gopher.shortlink.project.service.LinkAccessStatsService;
import org.springframework.stereotype.Service;

@Service
public class LinkAccessStatsServiceIml extends ServiceImpl<LinkAccessStatsMapper, LinkAccessStatsDO> implements LinkAccessStatsService {

}
