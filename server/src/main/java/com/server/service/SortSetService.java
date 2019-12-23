package com.server.service;

import com.google.common.collect.Lists;
import com.model.dto.FareDto;
import com.model.entity.PhoneFare;
import com.model.mapper.PhoneFareMapper;
import com.server.constants.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @Description
 */
@Service
public class SortSetService {
    @Autowired
    private PhoneFareMapper fareMapper;

    @Autowired
    private RedisTemplate redisTemplate;

//    @Transactional(rollbackFor = Exception.class)
//    public void add(PhoneFare phoneFare) {
//        phoneFare.setId(null);
//        fareMapper.insertSelective(phoneFare);
//        if (phoneFare.getId() > 0) {
//            ZSetOperations<String, PhoneFare> zSetOperations = redisTemplate.opsForZSet();
//            zSetOperations.add(Constant.RedisPhoneFarePrefix, phoneFare, phoneFare.getFare().doubleValue());
//        }
//    }

//    public Set<PhoneFare> getSortFare() {
//        String key = Constant.RedisPhoneFarePrefix;
//        ZSetOperations<String, PhoneFare> zSetOperations = redisTemplate.opsForZSet();
//        Long size = zSetOperations.size(key);
//        Set<PhoneFare> set = zSetOperations.range(key, 0, size);
//        return set;
//    }

    @Transactional(rollbackFor = Exception.class)
    public void add(PhoneFare phoneFare) {
        String key = Constant.RedisPhoneFarePrefix;
        phoneFare.setId(null);
        fareMapper.insertSelective(phoneFare);
        if (phoneFare.getId() > 0) {
            FareDto dto = new FareDto(phoneFare.getPhone());

            //value=phone,score=fare
            ZSetOperations<String, FareDto> zSetOperations = redisTemplate.opsForZSet();
            Double fare = zSetOperations.score(key, dto);
            if (fare == null) {
                //key value score
                zSetOperations.add(key, dto, phoneFare.getFare().doubleValue());
            } else {
                zSetOperations.incrementScore(key, dto, phoneFare.getFare().doubleValue());
            }

        }
    }

    public List<PhoneFare> getSortFare() {
        List<PhoneFare> list = Lists.newLinkedList();
        String key = Constant.RedisPhoneFarePrefix;
        ZSetOperations<String, FareDto> zSetOperations = redisTemplate.opsForZSet();
        Long size = zSetOperations.size(key);
        Set<ZSetOperations.TypedTuple<FareDto>> set = zSetOperations.rangeWithScores(key, 0, size);
        for (ZSetOperations.TypedTuple<FareDto> zset : set) {
            PhoneFare phoneFare = new PhoneFare();
            phoneFare.setPhone(zset.getValue().getPhone());
            phoneFare.setFare(BigDecimal.valueOf(zset.getScore()));
            list.add(phoneFare);
        }
        return list;
    }

}
