package org.example.application.port.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import org.example.infrastructure.configuration.enums.KafkaTopic;

public interface ProducerMessage<T> {

  void produce(T message, Map<String, Object> headers, KafkaTopic kafkaTopic)
      throws JsonProcessingException;
}
