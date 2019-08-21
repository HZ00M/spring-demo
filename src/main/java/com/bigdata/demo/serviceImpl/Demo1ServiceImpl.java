package com.bigdata.demo.serviceImpl;

import com.bigdata.demo.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class Demo1ServiceImpl implements DemoService {
    @Override
    public String userType() {
        return "nomal";
    }

    @Override
    public double discounFee(double fee) {
        return fee*0.9;
    }
}
