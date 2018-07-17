package com.jimo;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by jimo on 18-7-17.
 */
@Configuration
public class ESConfig {

    @Bean
    public TransportClient client() throws UnknownHostException {
        final InetSocketTransportAddress nodeAddress =
                new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300);

        final Settings settings = Settings.builder().put("cluster.name", "jimo").build();

        final PreBuiltTransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(nodeAddress);

        return client;
    }
}
