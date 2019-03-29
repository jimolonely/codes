package com.jimo.hystrixfeign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jimo
 * @date 19-3-29 下午5:19
 */
@RestController
public class TestController {

    @Resource
    private IUserService service;

    @GetMapping("/user/{name}")
    public String getUser(@PathVariable String name){
        return service.getUser(name);
    }
}