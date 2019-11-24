package com.server.controller;

import com.api.response.BaseResponse;
import com.api.response.StatusCode;
import com.model.entity.Item;
import com.server.service.StringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author admin
 * @Date 2019/11/23 23:09
 * @Description
 */
@RestController
@RequestMapping("string")
public class StringController {

    private static final Logger log = LoggerFactory.getLogger(StringController.class);

    @Autowired
    private StringService stringService;

    //添加
    @RequestMapping(value = "addItem", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse addItem(@RequestBody @Validated Item item, BindingResult result) {
        System.out.println(item.toString());
        if (result.hasErrors()) {
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            log.info("--商品信息：{}", item);
            stringService.addItem(item);

        } catch (Exception e) {
            log.error("--字符串String实战-商品详情存储-添加-发生异常：", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public BaseResponse getById(@RequestParam Integer id) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            response.setData(stringService.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }
}
