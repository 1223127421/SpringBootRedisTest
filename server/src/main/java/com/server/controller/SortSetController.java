package com.server.controller;

import com.api.response.BaseResponse;
import com.api.response.StatusCode;
import com.model.entity.PhoneFare;
import com.server.service.SortSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author admin
 * @Date 2019/12/10 21:07
 * @Description
 */
@RestController
@RequestMapping("/sort/set")
public class SortSetController extends AbstractController {

    @Autowired
    private SortSetService sortSetService;

    @RequestMapping("/add")
    public BaseResponse put(@RequestBody PhoneFare fare) {
        BaseResponse response = null;
        try {
            response = new BaseResponse(StatusCode.Success);
//            response.setData(sortSetService.add(fare));
            response.setData(sortSetService.add2(fare));
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse(StatusCode.Fail);
        }

        return response;
    }

    @RequestMapping("/get")
    public BaseResponse get() {
        BaseResponse response = null;
        try {
            response = new BaseResponse(StatusCode.Success);
            response.setData(sortSetService.get());
        } catch (Exception e) {
            e.printStackTrace();
            response = new BaseResponse(StatusCode.Fail);
        }

        return response;
    }


}
