package org.example.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SimpleProducer {


    private final KafkaTemplate<String, String> defaultProducer;

    public SimpleProducer(KafkaTemplate<String, String> defaultProducer) {
        this.defaultProducer = defaultProducer;
    }

    public void sendMessage(String message){
        defaultProducer.send("quickstart", message);
    }
}
