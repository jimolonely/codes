package com.jimo;

import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by jimo on 18-7-17.
 * 使用TransportClient的方式
 */
@RestController
@RequestMapping("/book/novel")
public class BookNovelController {

    private final TransportClient client;

    @Autowired
    public BookNovelController(TransportClient client) {
        this.client = client;
    }

    @GetMapping()
    public ResponseEntity getBook(@RequestParam(name = "id", defaultValue = "") String id) {
        if (id.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        final GetResponse result = client.prepareGet("book", "novel", id).get();
        if (!result.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(result.getSource(), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity addBook(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("word_count") int wordCount,
            @RequestParam("publish_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                    Date publishDate) {
        try {
            final XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("title", title)
                    .field("author", author)
                    .field("word_count", wordCount)
//                    .field("publish_date", publishDate.getTime())
                    .endObject();
            final IndexResponse result = client.prepareIndex("book", "novel")
                    .setSource(content).get();
            return new ResponseEntity(result.getId(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity deleteBook(@RequestParam("id") String id) {
        final DeleteResponse response = client.prepareDelete("book", "novel", id).get();
        return new ResponseEntity(response.getResult(), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity updateBook(
            @RequestParam("id") String id,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "author", required = false) String author) {
        final UpdateRequest updateRequest = new UpdateRequest("book", "novel", id);
        try {
            final XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            if (title != null) {
                builder.field("title", title);
            }
            if (author != null) {
                builder.field("author", author);
            }
            builder.endObject();
            updateRequest.doc(builder);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            final UpdateResponse updateResponse = client.update(updateRequest).get();
            return new ResponseEntity(updateResponse.getResult(), HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/query")
    public ResponseEntity queryBook(
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "gt_word_count", defaultValue = "0") Integer gtWordCount,
            @RequestParam(name = "lt_word_count", required = false) Integer ltWordCount) {
        final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (author != null) {
            boolQuery.must(QueryBuilders.matchQuery("author", author));
        }
        if (title != null) {
            boolQuery.must(QueryBuilders.matchQuery("title", title));
        }
        final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("word_count").from(gtWordCount);
        if (ltWordCount != null && ltWordCount >= gtWordCount) {
            rangeQuery.to(ltWordCount);
        }
        boolQuery.filter(rangeQuery);
        final SearchRequestBuilder builder = client.prepareSearch("book")
                .setTypes("novel")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQuery)
                .setFrom(0)
                .setSize(10);

        System.out.println(builder);

        final SearchResponse response = builder.get();
        List<Map<String, Object>> result = new ArrayList<>();

        for (SearchHit hit : response.getHits()) {
            result.add(hit.getSource());
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
