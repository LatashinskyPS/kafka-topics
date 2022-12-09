package by.intexsoft.consumer.service.recoverer;

import by.intexsoft.consumer.model.PublishEventDTO;
import by.intexsoft.consumer.service.business.DLQService;
import by.intexsoft.consumer.service.business.RetryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.stereotype.Service;

@Slf4j
@Service("mainMessageRecoverer")
public class MessageRecoverer implements ConsumerRecordRecoverer {

    private final ObjectMapper objectMapper;
    private final DLQService dlqService;
    private final RetryService retryService;


    public MessageRecoverer(ObjectMapper objectMapper, DLQService dlqService, RetryService retryService) {
        this.objectMapper = objectMapper;
        this.dlqService = dlqService;
        this.retryService = retryService;
    }

    @Override
    public void accept(ConsumerRecord<?, ?> consumerRecord, Exception e) {
        try {
            PublishEventDTO publishEvent = objectMapper.readValue(consumerRecord.value().toString(), PublishEventDTO.class);
            retryService.retryProcess(publishEvent);
        } catch (JsonProcessingException ex) {
            dlqService.handleOther(consumerRecord, e);
        }
    }
}
