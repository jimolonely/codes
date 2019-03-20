package com.jimo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ICacheServiceTest {

    @Resource
    private ICacheService<String, String> service;

    @Test
    public void set() {
        assertTrue(service.set("jimo-test", "test-value"));
    }
}