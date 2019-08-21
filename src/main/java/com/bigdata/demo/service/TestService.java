package com.bigdata.demo.service;

import com.bigdata.demo.interceptor.PageParam;
import com.bigdata.demo.util.Result;

import java.util.List;

public interface TestService {
    List findAllMonitor();

    List findAllBigdata();

    List testPage(PageParam pageParam);

    Result findAllBigdataWithRedis();

}
