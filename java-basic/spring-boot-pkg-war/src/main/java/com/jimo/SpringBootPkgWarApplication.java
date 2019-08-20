package com.jimo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jimo
 * @date 19-8-20 上午9:33
 */
@SpringBootApplication
@RestController
public class SpringBootPkgWarApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringBootPkgWarApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootPkgWarApplication.class, args);
    }

    @GetMapping({"/", "hello"})
    public String hello(String msg) {
        return "hello: " + msg;
    }

}
