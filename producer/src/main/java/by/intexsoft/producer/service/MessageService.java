package by.intexsoft.producer.service;

import by.intexsoft.producer.model.MessageDTO;
import by.intexsoft.producer.model.PublishEventDTO;

public interface MessageService {
    PublishEventDTO sendMessage(MessageDTO messageDTO);
}
