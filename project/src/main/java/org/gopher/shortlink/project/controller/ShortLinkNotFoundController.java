package org.gopher.shortlink.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 短链接不存在跳转控制器
 */
@Controller
// tip : Controller和RestController的一个很大区别是Controller返回的是视图，而RestController返回的是Json格式的字符串
public class ShortLinkNotFoundController {

    /**
     * 短链接不存在的跳转页面
     */
    @RequestMapping("/page/notfound")
    public String notfound() {
        return "notfound";
    }
}