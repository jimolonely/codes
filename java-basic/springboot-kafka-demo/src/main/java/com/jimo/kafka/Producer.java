package com.jimo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jimo
 * @date 19-3-19 下午2:36
 */
@Service
public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    static final String TOPIC = "users";

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) {
        logger.info(">>>>>------producer msg: [{}]", msg);
        kafkaTemplate.send(TOPIC, msg);
    }
}
