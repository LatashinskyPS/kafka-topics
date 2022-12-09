package by.intexsoft.consumer.service.business.impl;

import by.intexsoft.consumer.enums.State;
import by.intexsoft.consumer.exception.TelegramUnavailable;
import by.intexsoft.consumer.model.PublishEventDTO;
import by.intexsoft.consumer.model.TelegramResponse;
import by.intexsoft.consumer.service.business.EventService;
import by.intexsoft.consumer.service.business.KafkaService;
import by.intexsoft.consumer.service.business.TelegramService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final TelegramService telegramService;
    private final KafkaService kafkaService;
    private final ObjectMapper objectMapper;
    @Value("${topics.event-back}")
    private String EVENT_BACK_TOPIC;

    public EventServiceImpl(TelegramService telegramService, KafkaService kafkaService, ObjectMapper objectMapper) {
        this.telegramService = telegramService;
        this.kafkaService = kafkaService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleEvent(PublishEventDTO publishEventDTO) {
        TelegramResponse telegramResponse = telegramService.sendMessage(publishEventDTO.getMessage());

        if (!telegramResponse.getOk()) {
            throw new TelegramUnavailable();//TODO
        }

        log.info("Processing success message with id:{}", telegramResponse.getResult().getMessageId());

        publishEventDTO.setResult(telegramResponse.getResult());
        publishEventDTO.setState(State.COMPLETED);

        sendToEventBackTopic(publishEventDTO);
    }

    @Override
    public void processError(PublishEventDTO publishEventDTO) {
        publishEventDTO.setState(State.FAILED);
        log.info("Processing error? message with id:{}", publishEventDTO.getUuid());

        sendToEventBackTopic(publishEventDTO);
    }

    private void sendToEventBackTopic(PublishEventDTO publishEventDTO) {
        try {
            kafkaService.send(EVENT_BACK_TOPIC, UUID.randomUUID().toString(), objectMapper.writeValueAsString(publishEventDTO));
        } catch (JsonProcessingException e) {
            log.error("Can't handle exception:{}", e.getMessage());
        }
    }
}
