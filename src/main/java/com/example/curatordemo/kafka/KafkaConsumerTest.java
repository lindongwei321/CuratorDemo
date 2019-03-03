package com.example.curatordemo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerTest {
    @KafkaListener(topics = "test_topic")
    public void listen(ConsumerRecord<?,?> record) throws Exception{
        System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());

    }
}
