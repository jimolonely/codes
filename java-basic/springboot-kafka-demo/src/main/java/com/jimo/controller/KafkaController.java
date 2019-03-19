package com.jimo.controller;

import com.jimo.kafka.Producer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jimo
 * @date 19-3-19 下午2:45
 */
@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Resource
    private Producer producer;

    @GetMapping("/publish")
    public String sendMsg(@RequestParam("msg") String msg) {
        this.producer.sendMessage(msg);
        return "send msg[" + msg + "] success!";
    }

    @GetMapping("/send")
    public String sendMsgSync(@RequestParam("msg") String msg) {
        boolean ok = this.producer.sendMsgSync("key", msg);
        return "send msg[" + msg + "] success=" + ok + "!";
    }
}
