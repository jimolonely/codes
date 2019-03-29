package com.jimo.hystrixclient01;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

/**
 * @author jimo
 * @date 19-3-29 下午4:46
 */
@Service
public class UserService {

    @HystrixCommand(fallbackMethod = "defaultUser")
    public String getUser(String username) throws Exception {
        if (username.equals("jimo")) {
            return "yes, It's me";
        } else {
            throw new Exception();
        }
    }

    public String defaultUser(String username) {
        return "The user does not exist in this system!";
    }
}
