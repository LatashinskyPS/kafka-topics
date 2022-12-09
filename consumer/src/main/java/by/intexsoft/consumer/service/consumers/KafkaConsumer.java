package by.intexsoft.consumer.service.consumers;

import by.intexsoft.consumer.model.PublishEventDTO;
import by.intexsoft.consumer.model.RetryEventDTO;
import by.intexsoft.consumer.service.business.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final EventService eventService;

    public KafkaConsumer(ObjectMapper objectMapper, EventService eventService) {
        this.objectMapper = objectMapper;
        this.eventService = eventService;
    }

    @KafkaListener(topics = "${topics.main}", groupId = "telegram", containerFactory = "mainListenerContainerFactory")
    public void firstConsumer(String rowMessage) throws JsonProcessingException {
        PublishEventDTO eventDTO = objectMapper.readValue(rowMessage, PublishEventDTO.class);
        eventService.handleEvent(eventDTO);
    }

    @KafkaListener(topics = "${topics.retry}", groupId = "telegram-retry", containerFactory = "retryListenerContainerFactory")
    public void dlqConsumer(@Payload String rowMessage) throws JsonProcessingException {
        RetryEventDTO<PublishEventDTO> dlqMessage = objectMapper.readValue(rowMessage, new TypeReference<>() {
        });
        PublishEventDTO eventDTO = dlqMessage.getData();
        eventService.handleEvent(eventDTO);
    }
}
