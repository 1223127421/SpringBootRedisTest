package com.server.controller;

import com.api.response.BaseResponse;
import com.api.response.StatusCode;
import com.model.entity.Product;
import com.server.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author admin
 * @Date 2019/11/24 21:50
 * @Description
 */

@RestController
@RequestMapping("list")
public class ListController extends AbstractController {

    @Autowired
    private ListService listService;

    @RequestMapping(value = "put", method = RequestMethod.POST)
    public BaseResponse put(@RequestBody Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            listService.addPro(product);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public BaseResponse getByUserId(Integer userId) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            response.setData(listService.getByUserId(userId));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }
}
