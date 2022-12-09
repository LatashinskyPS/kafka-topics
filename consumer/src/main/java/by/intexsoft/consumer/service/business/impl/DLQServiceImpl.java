package by.intexsoft.consumer.service.business.impl;

import by.intexsoft.consumer.model.DLQMessageDTO;
import by.intexsoft.consumer.service.business.DLQService;
import by.intexsoft.consumer.service.business.KafkaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class DLQServiceImpl implements DLQService {
    private final KafkaService kafkaService;
    private final ObjectMapper objectMapper;

    @Value("${topics.dlq}")
    private String DLQ_TOPIC;

    public DLQServiceImpl(KafkaService kafkaService, ObjectMapper objectMapper) {
        this.kafkaService = kafkaService;
        this.objectMapper = objectMapper;
    }


    @Override
    public void handleOther(ConsumerRecord<?, ?> record, Exception e) {
        try {
            DLQMessageDTO dlqMessageDTO = new DLQMessageDTO(record.value().toString(), e.getMessage());
            kafkaService.send(DLQ_TOPIC, UUID.randomUUID().toString(), objectMapper.writeValueAsString(dlqMessageDTO));
        } catch (JsonProcessingException ex) {
            log.error("Can't handle exception:{}", e.getMessage());
        }
    }
}
