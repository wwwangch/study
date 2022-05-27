package com.wch.study.es8.operation;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.DynamicMapping;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.IndexOperation;
import co.elastic.clients.elasticsearch.core.bulk.UpdateOperation;
import co.elastic.clients.elasticsearch.indices.*;
import com.wch.study.es8.Es8TestApplication;
import com.wch.study.es8.entity.Dict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/5/27 14:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Es8TestApplication.class)
public class OperationTest {

    @Autowired
    private ElasticsearchClient client;


    @Test
    public void contextLoads() throws IOException {
        client.ping();
        System.out.println(11);
    }


    @Test
    public void createIndex() throws IOException {
        CreateIndexRequest request = CreateIndexRequest.of(builder ->
                builder.index("dict")
                        .mappings(mappingsBuilder ->
                                mappingsBuilder.dynamic(DynamicMapping.Strict)
                                        .properties("dictLabel", propertiesBuilder ->
                                                propertiesBuilder.text(tpb ->
                                                        tpb.analyzer("ik_max_word")))
                                        .properties("dictValue", propertiesBuilder -> propertiesBuilder.text(tpb -> tpb))

                        )
        );
        CreateIndexResponse createIndexResponse = client.indices().create(request);
        System.out.println(createIndexResponse.acknowledged());

    }

    @Test
    public void deleteIndex() throws IOException {
        DeleteIndexResponse dict = client.indices().delete(DeleteIndexRequest.of(req ->
                req.index("dict")));
        System.out.println(dict.acknowledged());
    }

    @Test
    public void updateMapping() throws IOException {

        PutMappingResponse putMappingResponse = client.indices().putMapping(PutMappingRequest.of(builder ->
                builder.index("dict")
                        .properties("createTime", probuilder ->
                                probuilder.date(dateBuilder ->
                                        dateBuilder.format("yyyy-MM-dd HH:mm:ss || yyyy-MM-dd || epoch_millis")))
        ));
        System.out.println(putMappingResponse.acknowledged());
    }

    @Test
    public void insert() throws IOException {
        Dict dict = new Dict();
        dict.setDictLabel("疯狂请安");
        dict.setDictValue("2332");
        dict.setCreateTime(new Date());
        IndexResponse indexResponse = client.index(IndexRequest.of(req ->
                req.index("dict")
                        .document(dict)));
        System.out.println(indexResponse.id());
    }

    @Test
    public void batchInsert() throws IOException {
        List<Dict> dicts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Dict dict = new Dict();
            dict.setDictLabel("i");
            dict.setDictValue("i=" + i);
            dict.setCreateTime(new Date());
            dicts.add(dict);
        }
        List<BulkOperation> bulkOperations = new ArrayList<>();
        Dict dict = new Dict();
        dict.setDictLabel("new1");
        dict.setDictValue("new1");
        bulkOperations.add(BulkOperation.of(builder -> builder.update(UpdateOperation.of(upbuilder -> upbuilder.id("TfrS_oABqCc1j0_y4aSJ").action(actBuilder -> actBuilder.doc(dict))))));
        for (int i = 1; i < 1000; i++) {
            int finalI = i;
            bulkOperations.add(BulkOperation.of(builder -> builder.index(IndexOperation.of(docBuilder -> docBuilder.document(dicts.get(finalI))))));
        }
        client.bulk(BulkRequest.of(req ->
                        req.index("dict")
                                .operations(bulkOperations
                                )
                )
        );
    }

    @Test
    public void deleteDoc() throws IOException {
        DeleteResponse delete = client.delete(DeleteRequest.of(builder -> builder.index("dict")
                .id("Tvrg_oABqCc1j0_yMaQ2")));
        System.out.println(delete.result());
    }
}
