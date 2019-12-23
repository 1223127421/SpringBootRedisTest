package com.server.service;

import com.model.entity.User;
import com.model.mapper.UserMapper;
import com.server.constants.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 */
@Service
public class SetService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void registerUser(User user) {
        user.setId(null);
        userMapper.insertSelective(user);
        if (user.getId() > 0) {
            SetOperations<String, String> setOperations = redisTemplate.opsForSet();
            setOperations.add(Constant.RedisSetUserPrefix, user.getEmail());
        }
    }

    //    判断邮箱是否已存在于缓存中
    public Boolean existEmail(String email) {
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        Boolean b = setOperations.isMember(Constant.RedisSetUserPrefix, email);
        if (b) {
            return true;
        } else {
            User user = userMapper.selectByEmail(email);
            if (user == null) {
                return false;
            } else {
                setOperations.add(Constant.RedisSetUserPrefix, user.getEmail());
                return true;
            }
        }
    }

}
