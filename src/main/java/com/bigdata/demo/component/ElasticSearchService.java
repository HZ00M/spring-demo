package com.bigdata.demo.component;

import com.alibaba.fastjson.JSON;
import com.bigdata.demo.config.ElasticSearchConfig;
import com.bigdata.demo.util.Result;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class ElasticSearchService   {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestHighLevelClient client;

    @Autowired
    ElasticSearchConfig config;

    /**
     * {
     *   "settings": {
     *     "number_of_shards": 2,
     *     "number_of_replicas": 0
     *   } ,
     *    "mappings": {
     *          "properties": {
     *           "name": {
     *             "type": "text",
     *             "analyzer": "ik_max_word"
     *           },
     *           "info":{
     *             "type":"text",
     *             "analyzer":"ik_max_word"
     *           },
     *           "price":{
     *             "type":"float"
     *           },
     *           "image":{
     *             "type":"keyword",
     *             "index":false
     *           }
     *         }
     *   }
     * }
     * @param map
     * @param index
     * @return
     */
    public Result createIndex(Map<String,Map> map,String index) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject()
                    .startObject("mappings")
                    .field("properties",map.get("mappings").get("properties"))
                    .endObject()
                    .startObject("settings")
                    .field("number_of_shards",map.get("settings").get("number_of_shards"))
                    .field("number_of_replicas",map.get("settings").get("number_of_replicas"))
                    .endObject()
                    .endObject();
            CreateIndexRequest request = new CreateIndexRequest(index).source(builder);
            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
            return new Result().success(response);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * {
     *     "name":"苹果手机",
     *     "info":"这一台街机，系统运行流畅",
     *     "price":3999,
     *     "image":"iphone.jpg"
     * }
     * @param index
     * @param id
     * @param map
     * @return
     */
    public Result insertOrUpdate(String index,String id,Map<String,?> map){
        try {
            IndexRequest request = new IndexRequest(index);
            request.id(id);
            request.source(map, XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            return new Result().success(response);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * 批量插入
     * @param index
     * @return
     */
    public Result insertBatch(String index,Map<String,Map> map){
        try {
            BulkRequest request = new BulkRequest(index);
            for (Map.Entry<String,Map> item :map.entrySet()){
                request.add(new IndexRequest(index).id(item.getKey()).source(item.getValue(),XContentType.JSON));
            }
            BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);
            return new Result().success(responses.getItems());
        }catch (Exception e){
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * 批量删除
     * @param index
     * @return
     */
    public Result deleteBatch(String index, Collection ids){
        try {
            BulkRequest request = new BulkRequest(index);
            ids.forEach(item->request.add(new DeleteRequest(index,item.toString())));
            BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);
            return new Result().success(responses.getItems());
        }catch (Exception e){
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * 条件删除
     */
    public void deleteByQuery(String index, QueryBuilder builder) {
        DeleteByQueryRequest request = new DeleteByQueryRequest(index);
        request.setQuery(builder);
        //设置批量操作数量,最大为10000
        request.setBatchSize(10000);
        request.setConflicts("proceed");
        try {
            client.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Result deleteIndex(String index){
        try {
            DeleteIndexRequest request = new DeleteIndexRequest(index);
            AcknowledgedResponse response = client.indices().delete(request,RequestOptions.DEFAULT);
            return new Result().success(response);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error();
        }
    }

    public Result deleteDocById(String index,String id) {
        try {
            DeleteRequest request = new DeleteRequest(index,id);
            DeleteResponse response = client.delete(request,RequestOptions.DEFAULT);
            return new Result().success(response);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error();
        }
    }

    public Result queryDoc(String index,QueryBuilder queryBuilder){
        try {
            SearchRequest request = new SearchRequest(index);
            SearchSourceBuilder sourceBuilder = searchSourceBuilder(queryBuilder);
            request.source(sourceBuilder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHits hits= response.getHits();
            List list = new ArrayList();
            for (SearchHit hit:hits){
                Object obj = JSON.parse(hit.getSourceAsString());
                list.add(obj);
            }
            return new Result().success(list);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error();
        }
    }
    public Result queryDoc(String index,QueryBuilder queryBuilder,List<AggregationBuilder> aggregationBuilders){
        try {
            SearchRequest request = new SearchRequest(index);
            SearchSourceBuilder sourceBuilder = searchSourceBuilder(queryBuilder,aggregationBuilders);
            request.source(sourceBuilder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHits hits= response.getHits();
            List list = new ArrayList();
            for (SearchHit hit:hits){
                Object obj = JSON.parse(hit.getSourceAsString());
                list.add(obj);
            }
            return new Result().success(list);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error();
        }
    }

    public Result queryDetailDoc(String index,QueryBuilder queryBuilder){
        try {
            SearchRequest request = new SearchRequest(index);
            SearchSourceBuilder sourceBuilder = searchSourceBuilder(queryBuilder);
            request.source(sourceBuilder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHits hits= response.getHits();
            return new Result().success(hits);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error();
        }
    }
    public Result queryDetailDoc(String index,QueryBuilder queryBuilder,List<AggregationBuilder> aggregationBuilders){
        try {
            SearchRequest request = new SearchRequest(index);
            SearchSourceBuilder sourceBuilder = searchSourceBuilder(queryBuilder,aggregationBuilders);
            request.source(sourceBuilder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//            SearchHits hits= response.getHits();
            return new Result().success(response);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error();
        }
    }

    private SearchSourceBuilder searchSourceBuilder(QueryBuilder queryBuilder){
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.size(config.getSize());
        sourceBuilder.query(queryBuilder);
        return sourceBuilder;
    }

    private SearchSourceBuilder searchSourceBuilder(QueryBuilder queryBuilder, List<AggregationBuilder> aggregationBuilders){
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.size(config.getSize());
        sourceBuilder.query(queryBuilder);
        for (AggregationBuilder aggregationBuilder:aggregationBuilders){
            sourceBuilder.aggregation(aggregationBuilder);
        }
        logger.info(sourceBuilder.toString());
        return sourceBuilder;
    }


}
