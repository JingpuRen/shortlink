package org.gopher.shortlink.admin.context;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.gopher.shortlink.admin.common.convention.exception.ClientException;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 用户信息传输过滤器
 */
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {

    private final StringRedisTemplate stringRedisTemplate;

    private static final List<String> IGNORE_URI = Lists.newArrayList(
            "/api/short-link/admin/v1/user/login",
            "/api/short-link/admin/v1/user/has-username"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // tip : 能使用这个，说明一定是登录上了的，感觉没有必要进行校验！！
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        // tip : 获取请求路径，方便我们后续进行更为详细的路径拦截
        String requestURI = httpServletRequest.getRequestURI();

        // tip : 判断具体的请求路径，只要不是特定的那几个，都要进行拦截
        if(!IGNORE_URI.contains(requestURI)){
            String method = httpServletRequest.getMethod();
            // 注册不需要拦截，并且由于Restful Api的缘故，我们需要判断方法
            if(!(Objects.equals(requestURI,"/api/short-link/admin/v1/user") && Objects.equals(method,"POST"))){
                // 获取请求头中的信息
                String username = httpServletRequest.getHeader("username");
                String token = httpServletRequest.getHeader("token");

                // tip : 判断如果参数中有一个为空，那么我们就给客户端返回提示信息
                if(!StrUtil.isAllNotBlank(username,token)){
                    throw new ClientException("请求未携带token或者username");
                }

                Object userInfoJsonStr = null;
                // tip : 不为空还不行，还需要检查一下用户信息是否真的存在
                try{
                    // 从Redis中获取用户的信息
                    userInfoJsonStr = stringRedisTemplate.opsForHash().get("login:" + username, token);

                    if(userInfoJsonStr == null){
                        throw new  ClientException("用户信息不存在或者不匹配");
                    }
                }catch (Exception ex){
                    // tip : 这里一般是因为Redis的内部出现了问题
                    throw new  ClientException("获取用户信息时出现的异常");
                }

                UserInfoDTO userInfoDTO = JSON.parseObject(userInfoJsonStr.toString(), UserInfoDTO.class);
                // 将登录用户放入到上下文中
                UserContext.setUser(userInfoDTO);
            }
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }
}