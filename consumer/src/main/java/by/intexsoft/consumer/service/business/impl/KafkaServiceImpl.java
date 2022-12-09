package by.intexsoft.consumer.service.business.impl;

import by.intexsoft.consumer.service.business.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class KafkaServiceImpl implements KafkaService {
    private final KafkaListenerEndpointRegistry registry;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaServiceImpl(KafkaListenerEndpointRegistry registry, KafkaTemplate<String, String> kafkaTemplate) {
        this.registry = registry;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void pause(long seconds) {
        pause();
        executorService.schedule(this::resume, seconds, TimeUnit.SECONDS);
    }

    public void pause() {
        registry.getListenerContainers().forEach(MessageListenerContainer::pause);
    }

    public void resume() {
        registry.getListenerContainers().forEach(MessageListenerContainer::resume);
    }

    @Override
    public void send(String topic, String key, String data) {
        kafkaTemplate.send(topic, key, data);
    }
}
