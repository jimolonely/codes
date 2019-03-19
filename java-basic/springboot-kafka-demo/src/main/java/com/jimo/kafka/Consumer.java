package com.jimo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author jimo
 * @date 19-3-19 下午2:40
 */
@Service
public class Consumer {
    private final static Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics = Producer.TOPIC)
    public void consumes(String msg) {
        logger.info("<<<<<------consumer msg:[{}]", msg);
    }

    @KafkaListener(topics = "${kafka.topic}")
    public void consumes2(String msg) {
        logger.info("<<<<<------consumer2 msg:[{}]", msg);
    }
}
