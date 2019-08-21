package com.bigdata.demo.serviceImpl;

import com.bigdata.demo.interceptor.PageParam;
import com.bigdata.demo.mapper.TestMapper;
import com.bigdata.demo.service.TestService;
import com.bigdata.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestMapper testMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List findAllMonitor() {
        List result =  testMapper.findAll();
        return result;
    }

    @Override
    public List findAllBigdata() {
        List result =  testMapper.findAll();
        return result;
    }

    public List testPage(PageParam pageParam){
        List result = testMapper.testPage(pageParam);
        return result;
    }

    @Override
    public Result findAllBigdataWithRedis() {
        if(redisTemplate.opsForValue().get("Result")!=null){
            Result result = (Result) redisTemplate.opsForValue().get("Result");
            System.out.println("获取redis数据"+result);
            return result;
        }
        System.out.println("获取数据库数据");
        List list =  testMapper.findAll();
        System.out.println("添加数据到缓存"+list.toString());
        Result result =  new Result().success(list);
        redisTemplate.opsForValue().set("Result", result);
        return result;
    }



}
