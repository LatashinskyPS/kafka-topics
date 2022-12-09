package by.intexsoft.consumer.service.business.impl;

import by.intexsoft.consumer.client.TelegramClient;
import by.intexsoft.consumer.model.MessageDTO;
import by.intexsoft.consumer.model.TelegramResponse;
import by.intexsoft.consumer.service.business.KafkaService;
import by.intexsoft.consumer.service.business.TelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class TelegramServiceImpl implements TelegramService {
    private final TelegramClient telegramClient;
    @Value("${consumer.bot.token}")
    private String botToken;
    private final KafkaService kafkaService;

    public TelegramServiceImpl(TelegramClient telegramClient, KafkaService kafkaService) {
        this.telegramClient = telegramClient;
        this.kafkaService = kafkaService;
    }

    @Override
    public TelegramResponse sendMessage(MessageDTO messageDTO) {
        try {
            return telegramClient.sendMessage(botToken, messageDTO.getChatId(), messageDTO.getMessage());
        } catch (Exception e) {
            kafkaService.pause(3);
            throw e;
        }
    }
}
