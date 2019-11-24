package com.server.controller;

import com.api.response.BaseResponse;
import com.api.response.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Author admin
 * @Date 2019/11/23 17:05
 * @Description
 */

@RestController
@RequestMapping("base")
public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String redisKey = "springbootredis:helloworld";

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String a() {
        System.out.println();
        return "hello world455";
    }

    @RequestMapping(value = "helloSet", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse helloWorldSet(@RequestParam String helloName) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            stringRedisTemplate.opsForValue().set(redisKey, helloName);
            response.setData("helloworld");
//            int a=1/0;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("----helloWorldSet异常：", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "helloGet", method = RequestMethod.GET)
    @ResponseBody
    public String helloWorldGet() {
        return stringRedisTemplate.opsForValue().get(redisKey);
    }
}
