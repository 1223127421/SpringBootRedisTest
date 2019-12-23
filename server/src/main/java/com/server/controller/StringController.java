package com.server.controller;

import com.api.response.BaseResponse;
import com.api.response.StatusCode;
import com.model.entity.Item;
import com.server.service.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 */

@RestController
@RequestMapping("/string")
public class StringController extends AbstractController {

    @Autowired
    private StringService stringService;

    @RequestMapping("add")
    public BaseResponse add(@RequestBody Item item) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            stringService.add(item);
        } catch (Exception e) {
            log.error("失败：", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    @RequestMapping("getById")
    public BaseResponse getById(Integer id) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            Item item = stringService.getById(id);
            response.setData(item);
        } catch (Exception e) {
            log.error("失败：", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    @RequestMapping("del")
    public BaseResponse del(Integer id) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            stringService.del(id);
        } catch (Exception e) {
            log.error("失败：", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }


}
