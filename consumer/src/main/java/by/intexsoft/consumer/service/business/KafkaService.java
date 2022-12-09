package by.intexsoft.consumer.service.business;

public interface KafkaService {
    void pause(long seconds);

    void send(String topic, String key, String data);
}
