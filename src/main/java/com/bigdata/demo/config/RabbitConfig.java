package com.bigdata.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class RabbitConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.publisher-confirms}")
    private Boolean confirm;

    @Value("${spring.rabbitmq.template.mandatory}")
    private Boolean mandatory ;

    public static final String DIRECT_EXCHANGE = "DirectExchange";
    public static final String FANOUT_EXCHANGE = "FanoutExchange";
    public static final String TOPIC_EXCHANGE = "TopicExchange";


    public static final String QUEUE_A = "QUEUE_A";
    public static final String QUEUE_B = "QUEUE_B";
    public static final String QUEUE_C = "QUEUE_C";
    public static final String QUEUE_D = "QUEUE_D";
    public static final String QUEUE_E = "QUEUE_E";

    public static final String ROUTINGKEY_A = "ROUTINGKEY.A";
    public static final String ROUTINGKEY_B = "ROUTINGKEY.B";
    public static final String ROUTINGKEY_C = "ROUTINGKEY.C";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(confirm);
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMandatory(mandatory);
        return template;
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitConfig.FANOUT_EXCHANGE);
    }

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE,false,false);
    }

    /**
     * 获取队列A
     *
     * @return
     */
    @Bean
    public Queue queueA() {
        return new Queue(QUEUE_A, true); //队列持久
    }

    /**
     * 获取队列B
     *
     * @return
     */
    @Bean
    public Queue queueB() {
        return new Queue(QUEUE_B, true); //队列持久
    }
    /**
     * 获取队列C
     *
     * @return
     */
    @Bean
    public Queue queueC() {
        return new Queue(QUEUE_C, true); //队列持久
    }
    /**
     * 获取队列D
     *
     * @return
     */
    @Bean
    public Queue queueD() {
        return new Queue(QUEUE_D, true); //队列持久
    }
    /**
     * 获取队列E
     *
     * @return
     */
    @Bean
    public Queue queueE() {
        return new Queue(QUEUE_E, true); //队列持久
    }

    /**
     *  1对1
     * @return
     */
    @Bean
    public Binding binding( ) {
        return BindingBuilder.bind(queueA()).to(directExchange()).with(RabbitConfig.ROUTINGKEY_A);
    }

    /**
     * 广播
     * @param queueB
     * @param fanoutExchange
     * @return
     */
    @Bean
    Binding bindingExchangeA(Queue queueB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueB).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeB(Queue queueC, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueC).to(fanoutExchange);
    }

    /**
     * 多对多
     * @return
     */
    @Bean
    public Binding bindingTopic(){
        return BindingBuilder.bind(queueD()).to(topicExchange()).with("lay.#");
    }
    @Bean
    public Binding bindingTopic2(){
        return BindingBuilder.bind(queueE()).to(topicExchange()).with("lay.*");
    }
}

