package com.server.controller;

import com.api.response.BaseResponse;
import com.api.response.StatusCode;
import com.model.entity.Product;
import com.model.entity.User;
import com.server.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 */
@RestController
@RequestMapping("/set")
public class SetController extends AbstractController {

    @Autowired
    private SetService setService;

    @RequestMapping("add")
    public BaseResponse add(@RequestBody User user) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            if (setService.existEmail(user.getEmail())) {
                response = new BaseResponse(StatusCode.Fail.getCode(), "邮箱已经存在！");
            } else {
                setService.registerUser(user);
            }
        } catch (Exception e) {
            log.error("失败：", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

}
