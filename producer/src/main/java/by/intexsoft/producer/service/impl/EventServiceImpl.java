package by.intexsoft.producer.service.impl;

import by.intexsoft.producer.entity.MessageEntity;
import by.intexsoft.producer.entity.PublishEventEntity;
import by.intexsoft.producer.enums.State;
import by.intexsoft.producer.mapper.ModelMapper;
import by.intexsoft.producer.model.MessageDTO;
import by.intexsoft.producer.model.PublishEventDTO;
import by.intexsoft.producer.repository.PublishEventRepository;
import by.intexsoft.producer.service.EventService;
import by.intexsoft.producer.service.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {
    private final ModelMapper mapper;
    private final PublishEventRepository publishEventRepository;
    private final KafkaProducer kafkaProducer;

    @Value("${topics.main}")
    private String MAIN_TOPIC;

    public EventServiceImpl(ModelMapper mapper, PublishEventRepository publishEventRepository, KafkaProducer kafkaProducer) {
        this.mapper = mapper;
        this.publishEventRepository = publishEventRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    @Transactional
    public PublishEventDTO processEvent(MessageDTO messageDTO) {
        PublishEventEntity eventEntity = createEventInDB(messageDTO);

        PublishEventDTO event = mapper.entityToDTO(eventEntity);

        kafkaProducer.sendEvent(MAIN_TOPIC, event);

        return event;
    }

    private PublishEventEntity createEventInDB(MessageDTO messageDTO) {
        MessageEntity messageEntity = mapper.DTOToEntity(messageDTO);
        PublishEventEntity publishEventEntity = new PublishEventEntity();

        publishEventEntity.setMessage(messageEntity);
        publishEventEntity.setState(State.CREATED);

        publishEventRepository.save(publishEventEntity);

        return publishEventEntity;
    }

    @Override
    @Transactional
    public void updateEventState(PublishEventDTO publishEventDTO) {
        PublishEventEntity eventFromDB = publishEventRepository.getReferenceById(publishEventDTO.getUuid());

        eventFromDB.setState(publishEventDTO.getState());
        eventFromDB.setResult(mapper.DTOToEntity(publishEventDTO.getResult()));

        publishEventRepository.save(eventFromDB);
    }

    @Override
    @Transactional
    public PublishEventDTO getById(UUID uuid) {
        try {
            PublishEventEntity eventFromDB = publishEventRepository.getReferenceById(uuid);

            return mapper.entityToDTO(eventFromDB);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND");
        }
    }
}
