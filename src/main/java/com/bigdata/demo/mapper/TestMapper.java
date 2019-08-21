package com.bigdata.demo.mapper;

import com.bigdata.demo.entity.Test;
import com.bigdata.demo.interceptor.PageParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestMapper {
    List findAll();

    List testPage(PageParam pageParam);

    Test findOne(int id);

    List<Test> findByIds(List<Integer> ids);
}
