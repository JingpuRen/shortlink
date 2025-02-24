package org.gopher.shortlink.admin.context;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;

/**
 * 用户信息传输过滤器
 */
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // tip : 能使用这个，说明一定是登录上了的，感觉没有必要进行校验！！
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        // 获取请求头中的信息
        String username = httpServletRequest.getHeader("username");
        String token = httpServletRequest.getHeader("token");

        // 从Redis中获取用户的信息
        Object object = stringRedisTemplate.opsForHash().get("login:" + username, token);
        assert object != null;
        UserInfoDTO userInfoDTO = JSON.parseObject(object.toString(), UserInfoDTO.class);

        // 将登录用户放入到上下文中
        UserContext.setUser(userInfoDTO);

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }
}