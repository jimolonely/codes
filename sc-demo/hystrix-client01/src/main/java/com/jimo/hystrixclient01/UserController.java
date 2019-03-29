package com.jimo.hystrixclient01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jimo
 * @date 19-3-29 下午4:49
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/user/{username}")
    public String getUser(@PathVariable String username) throws Exception {
        return userService.getUser(username);
    }
}
