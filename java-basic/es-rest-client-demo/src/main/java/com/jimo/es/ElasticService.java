package com.jimo.es;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jimo
 * @date 19-3-22 下午3:19
 */
@Service
public class ElasticService {

    @Resource
    private RestHighLevelClient client;


}
