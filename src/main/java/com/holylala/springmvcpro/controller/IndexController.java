package com.holylala.springmvcpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhenqionghuang@creditease.cn
 * @version V1.0
 * @Package com.holylala.springmvcpro.controller
 * @Description:
 * @date 2016/9/26 15:26
 * @Company: creditease.cn
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/","/indexa"},method = {RequestMethod.GET})
    @ResponseBody
    public String index() {
        return  "hello world.";
    }

    //url上的参数
    //http://localhost:8080/profile/123
    @RequestMapping("/profile/{userId}")
    @ResponseBody
    public String indexWithPathParam(@PathVariable("userId") String userId) {
        return "get userId:>>"+userId;
    }

    //请求参数
    //http://localhost:8080/param?type=1&key=2
    @RequestMapping("/param")
    @ResponseBody
    public String indexWithRequestParam(@RequestParam(value = "type") int type,
                                        @RequestParam(value = "key",defaultValue = "keydefault",required = false) String key) {
        return "get Request Param:>>>"+type+",key:>>>"+key;
    }


}
