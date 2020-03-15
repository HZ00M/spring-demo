package com.bigdata.demo.component;

import com.bigdata.demo.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
//@RabbitListeners({@RabbitListener(queues =  {RabbitConfig.QUEUE_A} ),@RabbitListener(queues =  {RabbitConfig.QUEUE_B} ),@RabbitListener(queues =  {RabbitConfig.QUEUE_C} )})
public class MsgReceiver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues =  {RabbitConfig.QUEUE_A} )
    public void processA( Object content ) {
        logger.info("接收处理队列A当中的消息： " + content);
    }
    @RabbitListener(queues =  {RabbitConfig.QUEUE_B} )
    public void processB(Object content) {
        logger.info("接收处理队列B当中的消息： " + content);
    }
    @RabbitListener(queues =  {RabbitConfig.QUEUE_C} )
    public void processC(Object content) {
        logger.info("接收处理队列C当中的消息： " + content);
    }
    @RabbitListener(queues = {RabbitConfig.QUEUE_D})
    public void processD(Object content){logger.info("接收处理队列D当中的消息： " + content);}
    @RabbitListener(queues={RabbitConfig.QUEUE_E})
    public void processE(Object content){
        logger.info("接收处理队列E当中的消息： " + content);
    }
}

