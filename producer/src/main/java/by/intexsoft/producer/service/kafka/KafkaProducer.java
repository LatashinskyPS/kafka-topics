package by.intexsoft.producer.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class KafkaProducer {


    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String topic, Object data) {
        try {
            String dataAsString = objectMapper.writeValueAsString(data);
            kafkaTemplate.send(topic, UUID.randomUUID().toString(), dataAsString);
        } catch (JsonProcessingException e) {
            //todo
        }
    }
}
