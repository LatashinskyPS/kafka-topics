package by.intexsoft.consumer.service.business;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface DLQService {
    void handleOther(ConsumerRecord<?, ?> record, Exception e);
}
