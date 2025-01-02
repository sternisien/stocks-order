package org.example.infrastructure.configuration;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.infrastructure.configuration.bean.KafkaConfigServer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaDefaultConfigurationProducer {

  @Bean
  @ConfigurationProperties(prefix = "kafka.bootstrap-server")
  public KafkaConfigServer getKafkaConfigServer() {
    return new KafkaConfigServer();
  }

  @Bean(name = "kafka-settings")
  public Map<String, Object> configurationSettings(KafkaConfigServer kafkaConfigServer) {
    Map<String, Object> settings = new HashMap<>();
    settings.put(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        String.format("%s:%s", kafkaConfigServer.getHost(), kafkaConfigServer.getPort()));
    settings.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    settings.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    settings.put(ProducerConfig.RETRIES_CONFIG, 20);
    return settings;
  }

  /**
   * Fonction permettant de définir un producer générique qui s'adapte à n'importe quel type de
   * message et de le paramétrer
   *
   * @param settings les paramètres définissant la configuration
   * @return un producer configuré avec la configuration fournise
   * @param <T> message générique
   */
  @Bean
  public <T> DefaultKafkaProducerFactory<String, T> getProducerFactory(
      @Qualifier("kafka-settings") Map<String, Object> settings) {
    return new DefaultKafkaProducerFactory<>(settings);
  }

  /**
   * Fonction associant le producer au template kafka
   *
   * @param defaultKafkaProducerFactory le producer
   * @return le template créé
   * @param <T> message générique
   */
  @Bean
  public <T> KafkaTemplate<String, T> kafkaTemplate(
      DefaultKafkaProducerFactory<String, T> defaultKafkaProducerFactory) {
    return new KafkaTemplate<>(defaultKafkaProducerFactory);
  }
}
