package by.intexsoft.producer.service;

import by.intexsoft.producer.model.MessageDTO;
import by.intexsoft.producer.model.PublishEventDTO;

import java.util.UUID;

public interface EventService {
    PublishEventDTO processEvent(MessageDTO messageDTO);

    void updateEventState(PublishEventDTO publishEventDTO);

    PublishEventDTO getById(UUID uuid);
}
