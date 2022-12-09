package by.intexsoft.consumer.service.business;

import by.intexsoft.consumer.model.PublishEventDTO;
import by.intexsoft.consumer.model.RetryEventDTO;

public interface RetryService {
    void retryProcess(PublishEventDTO publishEventDTO);

    void retryProcess(RetryEventDTO<PublishEventDTO> retryEvent);
}
