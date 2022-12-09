package by.intexsoft.consumer.service.business;

import by.intexsoft.consumer.model.PublishEventDTO;

public interface EventService {
    void handleEvent(PublishEventDTO publishEventDTO);

    void processError(PublishEventDTO publishEventDTO);
}
