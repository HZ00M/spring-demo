package com.bigdata.demo.controller;

import com.bigdata.demo.annotation.DataSource;
import com.bigdata.demo.annotation.DistributedLock;
import com.bigdata.demo.component.MsgProducer;
import com.bigdata.demo.concurrent.CountDownLatchTask;
import com.bigdata.demo.concurrent.CyclicBarrierTask;
import com.bigdata.demo.config.ZKConfig;
import com.bigdata.demo.exception.GlobalException;
import com.bigdata.demo.interceptor.PageParam;
import com.bigdata.demo.service.ConcurrentService;
import com.bigdata.demo.service.TestService;
import com.bigdata.demo.serviceImpl.DiscountServiceImpl;
import com.bigdata.demo.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping("/test")
@Api(value = "测试Controller")
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    ConcurrentService concurrentService;

//    @Autowired
//    ZKConfig zkConfig;

//    @Autowired
    MsgProducer producer;

    @Autowired
    DiscountServiceImpl discountService;

    @ApiOperation(value = "切换数据源monitor",notes = "切换monitor数据源")
    @RequestMapping(value = "/findAllMonitor",method = RequestMethod.GET)
    @DataSource(name = "monitor")
    public Result findAllMonitor() {
        List list = testService.findAllMonitor();
        return new Result().success(list);
    }


    @ApiOperation(value = "切换数据源bigdata",notes = "切换bigdata数据源")
    @RequestMapping(value = "/findAllBigdata",method = RequestMethod.GET)
    @DataSource(name = "bigdata")
    public Result findAllBigdata() {
        List list = testService.findAllBigdata();
        return new Result().success(list);
    }

    /**
     * path parameters, such as /users/{id}
     * query parameters, such as /users?role=admin
     * header parameters, such as X-MyHeader: Value
     * cookie parameters, which are passed in the Cookie header, such as Cookie: debug=0; csrftoken=BUSe35dohU3O1MZvDCU
     */
    @ApiOperation(value = "打折",notes = "打折接口，策略模式")
    @ApiImplicitParams({@ApiImplicitParam(name = "type",value = "用户类型",required = true,dataType = "String",paramType = "query"),
    @ApiImplicitParam(name = "fee",value = "价格",required = true,dataType = "Double",paramType = "query",example = "{type:vip,fee:100.00}")})
    @RequestMapping(value = "/discount",method = RequestMethod.POST)
    public double discount(String type,double fee){
        return discountService.doDiscount(type,fee);
    }

    @ApiIgnore //使用该注解忽略这个API
    @RequestMapping("/testPage")
    public List testPage(){
        PageParam pageParam = new PageParam(1,2,true,true,0,0);
        return testService.testPage(pageParam);
    }
    @ApiIgnore //使用该注解忽略这个API
    @RequestMapping("/excptionTest")
    public Result excptionTest() throws Exception {

        try {
            int a = 1/0;
        }catch (Exception e){
            throw new GlobalException(400,"算术异常");
        }
        return Result.success();
    }

    @ApiIgnore //使用该注解忽略这个API
    @RequestMapping("/excptionTest2")
    public Result excptionTest2() throws Exception {

        try {
            int a = 1/0;
        }catch (Exception e){
            throw new GlobalException(400,"算术错误",e);
        }
        return Result.success();
    }

    @ApiIgnore //使用该注解忽略这个API
    @RequestMapping("/excptionTest3")
    public Result ResultTest() throws Exception {
        return new Result().success();
    }

    @RequestMapping("/findAllBigdataWithRedis")
    public Result findAllBigdataWithRedis(){
        return testService.findAllBigdataWithRedis();
    }

    @ApiIgnore //使用该注解忽略这个API
    @RequestMapping("/test")
    public void Test() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        Executor executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.execute(new CyclicBarrierTask(cyclicBarrier, testService::findAllBigdataWithRedis));
        }
    }

    @RequestMapping("/testException")
    public void testException() throws GlobalException {
        throw new GlobalException(400,"testException");
    }

    //配置中心
//    @ApiIgnore //使用该注解忽略这个API
//    @RequestMapping("/testZookeeper")
//    public void testZookeeper(){
//        String str1 = zkConfig.getConfig1();
//        String str2 = zkConfig.getConfig2();
//        System.out.println(str1);
//    }

    @ApiIgnore //使用该注解忽略这个API
    @RequestMapping("/testCountDown")
    public void testCountDown(){
        List<Runnable> tasks = new ArrayList<>();
        for (int i =0;i<10;i++){
            Thread t = new Thread(  new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程执行");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"thread" + i);
            tasks.add(t);
        }
        CountDownLatch countDownLatch = new CountDownLatch(9);
        CountDownLatchTask task = new CountDownLatchTask(countDownLatch,tasks);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(task);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行结束");
    }

    @ApiIgnore //使用该注解忽略这个API
    @RequestMapping("/queryOne")
    public Result queryOne(int id){
        producer.sendMsg("queryOne");
        return concurrentService.queryOne(id);
    }

    @ApiIgnore //使用该注解忽略这个API
    @RequestMapping("/queryTwo")
    public Result queryTwo(int id){
        producer.sendMsgFanout("queryTwo");
        return concurrentService.queryOne(id);
    }

    //批量接口调用
    @ApiIgnore //使用该注解忽略这个API
    @RequestMapping("queryTest")
    public Result queryTest(int id)throws InterruptedException,ExecutionException{
        return concurrentService.queryTest(id);
    }

    @DistributedLock(expire = 1000,timeout = 1000)
    @RequestMapping("testLock")
    public Result testLock(){
        try {
            System.out.println("模拟执行业务逻辑。。。");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.error();
        }
        return Result.success();
    }
}
