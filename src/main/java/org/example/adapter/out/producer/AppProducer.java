package org.example.adapter.out.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.application.port.out.ProducerMessage;
import org.example.infrastructure.configuration.enums.KafkaTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class AppProducer<T> implements ProducerMessage<T> {

  private final KafkaTemplate<String, Message<T>> kafkaTemplate;

  public AppProducer(KafkaTemplate<String, Message<T>> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void produce(T message, Map<String, Object> headers, KafkaTopic kafkaTopic)
      throws JsonProcessingException {
    Message<T> msgToSend = new GenericMessage<>(message, headers);
    kafkaTemplate.send(new ProducerRecord<>(kafkaTopic.getTopic(), msgToSend));
  }
}
