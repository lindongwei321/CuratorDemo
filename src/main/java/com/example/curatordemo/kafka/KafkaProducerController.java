package com.example.curatordemo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class KafkaProducerController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @RequestMapping("send")
    public String send(String msg){
        kafkaTemplate.send("",msg);
        return "sucess";
    }
}
