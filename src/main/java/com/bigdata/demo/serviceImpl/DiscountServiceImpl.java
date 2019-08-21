package com.bigdata.demo.serviceImpl;

import com.bigdata.demo.service.DemoService;
import com.bigdata.demo.service.DiscountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模板模式
 */
@Service
public class DiscountServiceImpl implements DiscountService {
    List<DemoService> demoServiceList;

    public DiscountServiceImpl(List<DemoService> demoServiceList){
        this.demoServiceList = demoServiceList;
    }

    public double doDiscount(String type,double fee){
        double discountFee = 0;
        for (DemoService demoService :demoServiceList){
            if (demoService.userType().equals(type)){
               discountFee = demoService.discounFee(fee);
            }
        }
        return discountFee;
    }
}
