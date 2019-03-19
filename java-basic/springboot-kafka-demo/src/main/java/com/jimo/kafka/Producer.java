package com.jimo.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

    @Value("${kafka.topic}")
    private String topic;

    public boolean sendMsgSync(String key, String msg) {
        logger.info(">>>>>------producer msg: [{}]", msg);

        final boolean[] ok = {true};
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, msg);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(@NonNull Throwable throwable) {
                logger.error("sent message=[{}] failed!", msg, throwable);
                ok[0] = false;
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("sent message=[{}] with offset=[{}] success!", msg, result.getRecordMetadata().offset());
            }
        });
        try {
            // 因为是异步发送，所以我们等待，最多10s
            future.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.error("waiting for kafka send finish failed!", e);
            return false;
        }
        return ok[0];
    }
}
