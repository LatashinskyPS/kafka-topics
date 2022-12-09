package by.intexsoft.producer.controller;

import by.intexsoft.producer.model.MessageDTO;
import by.intexsoft.producer.model.PublishEventDTO;
import by.intexsoft.producer.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Message Controller", description = "For operation's with message(send)")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @PostMapping("/publish-message")
    @Operation(summary = "Send message to chat id")
    public PublishEventDTO publishMessage(@RequestBody MessageDTO messageDTO) {
        return messageService.sendMessage(messageDTO);
    }
}
