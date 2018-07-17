package com.jimo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootElasticsearchDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootElasticsearchDemoApplication.class, args);
    }
}
