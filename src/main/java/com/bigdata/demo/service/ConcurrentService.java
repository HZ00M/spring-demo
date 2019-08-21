package com.bigdata.demo.service;

import com.bigdata.demo.entity.Test;
import com.bigdata.demo.util.Result;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ConcurrentService {
    Test findOne(int id);

    List<Test> findByIds(List ids);

    Result queryOne(int id);

    Result queryTest(int id)throws InterruptedException, ExecutionException;
}
