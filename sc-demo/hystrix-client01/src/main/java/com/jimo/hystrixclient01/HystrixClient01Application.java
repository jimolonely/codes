package com.jimo.hystrixclient01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author jimo
 * @date 19-3-29 下午4:44
 */
@EnableHystrix
@SpringBootApplication
@EnableDiscoveryClient
public class HystrixClient01Application {

    public static void main(String[] args) {
        SpringApplication.run(HystrixClient01Application.class, args);
    }

}
