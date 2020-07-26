package com.bigdata.demo.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



@Component
@ConfigurationProperties(prefix = "elasticsearch")
@Data
public class ElasticSearchConfig {
    private String ip;
    private int port;
    private int size = 10;

    @Bean
    @Scope("prototype")
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(restClientBuilder());
        return client;
    }

    @Bean
    @Scope("prototype")
    public RestClientBuilder restClientBuilder(){
        return RestClient.builder(new HttpHost(ip,port,HttpHost.DEFAULT_SCHEME_NAME));
    }


}
