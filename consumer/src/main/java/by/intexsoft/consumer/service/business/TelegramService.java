package by.intexsoft.consumer.service.business;

import by.intexsoft.consumer.model.MessageDTO;
import by.intexsoft.consumer.model.TelegramResponse;


public interface TelegramService {

    TelegramResponse sendMessage(MessageDTO messageDTO);
}
