package com.jimo.hystrixfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author jimo
 * @date 19-3-29 下午5:14
 */
@FeignClient(name = "hystrix-client01", fallback = UserServiceFallback.class)
public interface IUserService {

    @GetMapping("/user/{username}")
    String getUser(@PathVariable String username);
}
