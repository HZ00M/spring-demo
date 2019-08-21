package com.bigdata.demo.serviceImpl;

import com.bigdata.demo.entity.Test;
import com.bigdata.demo.mapper.TestMapper;
import com.bigdata.demo.service.ConcurrentService;
import com.bigdata.demo.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class ConcurrentServiceImpl implements ConcurrentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    TestMapper testMapper;
    @Override
    public Test findOne(int id) {
        return testMapper.findOne(id);
    }

    @Override
    public List findByIds(List ids){
        return testMapper.findByIds(ids);
    }


    public Result queryOne(int id){
        Test test = findOne(id);
        Result result = new Result().success(test);
        return result;
    }

    public Result queryTest(int id)throws InterruptedException,ExecutionException{
        Request request = new Request();
        CompletableFuture<Test> completableFuture = new CompletableFuture<>();
        request.map.put("id",id);
        request.future = completableFuture;
        queue.add(request);

        Result result =  new Result().success(completableFuture.get());
        return result;
    }
    /**
     * 并发调用转批量调用
     */
    //通用请求参数
    public class Request{
        public Map<String,Object> map = new HashMap<>();
        public CompletableFuture future;
    }
    //收集所有请求放入队列中
    LinkedBlockingDeque<Request> queue = new LinkedBlockingDeque<>();


    //定时获取请求
    @PostConstruct
    public void init(){
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(()->{
            //执行定时任务
            int size = queue.size();
            if(size==0){
                return;
            }
            ArrayList<Request> list = new ArrayList<>();
            for(int i =0;i<size;i++){
                list.add(queue.poll())   ;
            }
            System.out.println("批量出来了"+size+"个请求");
            ArrayList<Integer> idList = new ArrayList<>();
            for (Request request :list){
                idList.add((Integer) request.map.get("id"));
            }
            List<Test>  responeses = findByIds(idList);
            Map<Integer,Test> responeseMap = new HashMap();
            for(Test test:responeses){
                responeseMap.put(test.getId(),test);
            }
            for (Request request :list){
                Test rs = responeseMap.get(request.map.get("id"));
                request.future.complete(rs);
            }

        }, 0,10, TimeUnit.SECONDS);
    }
}
