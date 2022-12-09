package by.intexsoft.producer.service.impl;

import by.intexsoft.producer.model.MessageDTO;
import by.intexsoft.producer.model.PublishEventDTO;
import by.intexsoft.producer.service.EventService;
import by.intexsoft.producer.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private final EventService eventService;

    public MessageServiceImpl(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public PublishEventDTO sendMessage(MessageDTO messageDTO) {
        return eventService.processEvent(messageDTO);
    }
}
