package com.server.service;

import com.model.entity.Notice;
import com.model.entity.User;
import com.model.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author admin
 * @Date 2019/11/26 0:15
 * @Description
 */
@Service
public class EmailService {

    @Autowired
    private UserMapper userMapper;

    public void putNotice(Notice notice) {
        List<User> list = userMapper.selectList();
        list.stream().forEach(n -> {
            System.out.println("email:" + n.getEmail() + ",content:" + notice.getContent());
        });
    }

}
