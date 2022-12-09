package by.intexsoft.producer.service.kafka;

import by.intexsoft.producer.model.PublishEventDTO;
import by.intexsoft.producer.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final EventService eventService;

    public KafkaConsumer(ObjectMapper objectMapper, EventService eventService) {
        this.objectMapper = objectMapper;
        this.eventService = eventService;
    }

    @KafkaListener(topics = "${topics.event-back}", groupId = "telegram", containerFactory = "mainListenerContainerFactory")
    public void firstConsumer(String rowMessage) throws JsonProcessingException {
        PublishEventDTO eventDTO = objectMapper.readValue(rowMessage, PublishEventDTO.class);
        eventService.updateEventState(eventDTO);
    }
}
