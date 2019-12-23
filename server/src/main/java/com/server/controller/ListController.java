package com.server.controller;

import com.api.response.BaseResponse;
import com.api.response.StatusCode;
import com.model.entity.Notice;
import com.model.entity.Product;
import com.server.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 */
@RestController
@RequestMapping("/list")
public class ListController extends AbstractController {

    @Autowired
    private ListService listService;

    @RequestMapping("add")
    public BaseResponse add(@RequestBody Product product) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            listService.add(product);
        } catch (Exception e) {
            log.error("失败：", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    @RequestMapping("getByUserId")
    public BaseResponse getByUserId(Integer userId) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            response.setData(listService.getByUserId(userId));
        } catch (Exception e) {
            log.error("失败：", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    @RequestMapping("addNotice")
    public BaseResponse add(@RequestBody Notice notice) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            listService.addNotice(notice);
        } catch (Exception e) {
            log.error("失败：", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

}
