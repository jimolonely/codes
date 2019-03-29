package com.jimo.hystrixfeign;

import org.springframework.stereotype.Component;

/**
 * @author jimo
 * @date 19-3-29 下午5:16
 */
@Component
public class UserServiceFallback implements IUserService {

    @Override
    public String getUser(String username) {
        return "The user does not exist, please guess the name!";
    }
}
