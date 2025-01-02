package org.example.infrastructure.configuration.enums;

public enum KafkaTopic {
  LOCK_ASSET_PORTFOLIO("lock-asset-portfolio");

  private final String topic;

  KafkaTopic(String topic) {
    this.topic = topic;
  }

  public String getTopic() {
    return topic;
  }
}
