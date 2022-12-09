package by.intexsoft.consumer.service.business.impl;

import by.intexsoft.consumer.model.PublishEventDTO;
import by.intexsoft.consumer.model.RetryEventDTO;
import by.intexsoft.consumer.service.business.DLQService;
import by.intexsoft.consumer.service.business.EventService;
import by.intexsoft.consumer.service.business.KafkaService;
import by.intexsoft.consumer.service.business.RetryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class RetryServiceImpl implements RetryService {

    private final ObjectMapper objectMapper;

    private final KafkaService kafkaService;
    private final EventService eventService;
    private final DLQService dlqService;
    @Value("${max-retry}")
    private long MAX_RETRY;
    @Value("${topics.retry}")
    private String RETRY_TOPIC;

    public RetryServiceImpl(ObjectMapper objectMapper, KafkaService kafkaService, EventService eventService, DLQService dlqService) {
        this.objectMapper = objectMapper;
        this.kafkaService = kafkaService;
        this.eventService = eventService;
        this.dlqService = dlqService;
    }

    @Override
    public void retryProcess(PublishEventDTO publishEventDTO) {
        RetryEventDTO<PublishEventDTO> retryEvent = new RetryEventDTO<>(publishEventDTO, 0);
        retryProcess(retryEvent);
    }

    @Override
    public void retryProcess(RetryEventDTO<PublishEventDTO> retryEvent) {
        try {
            if (retryEvent.getRetryCount() > MAX_RETRY) {
                eventService.processError(retryEvent.getData());
            } else {
                retryEvent.setRetryCount(retryEvent.getRetryCount() + 1);
                kafkaService.send(RETRY_TOPIC, UUID.randomUUID().toString(), objectMapper.writeValueAsString(retryEvent));
            }
        } catch (JsonProcessingException e) {
            log.error("Can't handle exception:{}", e.getMessage());
        }
    }
}
