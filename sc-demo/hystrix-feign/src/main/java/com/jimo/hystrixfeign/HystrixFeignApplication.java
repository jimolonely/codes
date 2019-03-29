package com.jimo.hystrixfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jimo
 * @date 19-3-29 下午5:14
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class HystrixFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixFeignApplication.class, args);
    }

}
