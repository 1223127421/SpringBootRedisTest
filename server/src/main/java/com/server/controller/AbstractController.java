package com.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * @Author admin
 * @Date 2019/11/24
 * @Description
 */
@Controller
public abstract class AbstractController {

    public final Logger log = LoggerFactory.getLogger(getClass());

}
