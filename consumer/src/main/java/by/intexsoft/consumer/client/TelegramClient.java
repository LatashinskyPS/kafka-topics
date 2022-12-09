package by.intexsoft.consumer.client;

import by.intexsoft.consumer.model.TelegramResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "telegram-client", url = "https://api.telegram.org")
public interface TelegramClient {

    @RequestMapping(method = RequestMethod.POST, value = "/bot{bot_token}/sendMessage")
    TelegramResponse sendMessage(@PathVariable(value = "bot_token") String botToken,
                                 @RequestParam(value = "chat_id") Long chatId,
                                 @RequestParam(value = "text") String text);
}
