package com.jimo.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jimo
 * @date 19-3-22 下午3:19
 */
@Configuration
public class EsConfig {

    private RestClient restClient() {
        return RestClient.builder(
                new HttpHost("192.168.17.70", 9300),
                new HttpHost("192.168.17.69", 9300)
        ).build();
    }

    @Bean
    public RestHighLevelClient highLevelClient() {
        return new RestHighLevelClient(restClient());
    }
}
