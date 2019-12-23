package com.server.controller;

import com.api.response.BaseResponse;
import com.api.response.StatusCode;
import com.model.entity.Item;
import com.model.entity.PhoneFare;
import com.server.service.SortSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 */
@RestController
@RequestMapping("/sort/set")
public class SortSetController extends AbstractController {

    @Autowired
    private SortSetService sortSetService;

    @RequestMapping("add")
    public BaseResponse add(@RequestBody PhoneFare phoneFare) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            sortSetService.add(phoneFare);
        } catch (Exception e) {
            log.error("失败：", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    @RequestMapping("get")
    public BaseResponse get() {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            response.setData(sortSetService.getSortFare());
        } catch (Exception e) {
            log.error("失败：", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

}
