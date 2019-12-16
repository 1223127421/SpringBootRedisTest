package com.server.service;

import com.model.dto.FareDto;
import com.model.entity.PhoneFare;
import com.model.mapper.PhoneFareMapper;
import com.server.constants.Constant;
import com.sun.org.apache.bcel.internal.generic.FADD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @Author admin
 * @Date 2019/12/10 21:10
 * @Description
 */
@Service
public class SortSetService {

    @Autowired
    private PhoneFareMapper fareMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    public Integer add(PhoneFare fare) {
        int res = fareMapper.insertSelective(fare);
        if (res > 0) {
            ZSetOperations<String, PhoneFare> zSetOperations = redisTemplate.opsForZSet();
            zSetOperations.add(Constant.RedisPhoneFarePrefix, fare, fare.getFare().doubleValue());
        }
        return fare.getId();
    }

    public Set<PhoneFare> get() {
        String key = Constant.RedisPhoneFarePrefix;
        ZSetOperations<String, PhoneFare> zSetOperations = redisTemplate.opsForZSet();
        Long size = zSetOperations.size(key);
        return zSetOperations.reverseRange(key, 0, size);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer add2(PhoneFare fare) {
        int res = fareMapper.insertSelective(fare);
        if (res > 0) {
            FareDto fareDto = new FareDto();
            fareDto.setPhone(fare.getPhone());
            ZSetOperations<String, FareDto> zSetOperations = redisTemplate.opsForZSet();

            Double oldFare = zSetOperations.score(Constant.RedisPhoneFarePrefix2, fareDto);
            if (oldFare != null) {
                zSetOperations.incrementScore(Constant.RedisPhoneFarePrefix2, fareDto, fare.getFare().doubleValue());
            } else {
                zSetOperations.add(Constant.RedisPhoneFarePrefix2, fareDto, fare.getFare().doubleValue());
            }
        }
        return fare.getId();
    }


    public void get2(){
        String key=Constant.RedisPhoneFarePrefix2;
        ZSetOperations<String,FareDto>zSetOperations=redisTemplate.opsForZSet();
        Long size=zSetOperations.size(key);

      Set<FareDto> set= zSetOperations.range(key,0,size);
      Set<FareDto> setScore= zSetOperations.rangeByScore(key,0,size);


    }
}
