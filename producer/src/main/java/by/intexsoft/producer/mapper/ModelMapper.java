package by.intexsoft.producer.mapper;

import by.intexsoft.producer.entity.MessageEntity;
import by.intexsoft.producer.entity.PublishEventEntity;
import by.intexsoft.producer.entity.TelegramResultEntity;
import by.intexsoft.producer.model.MessageDTO;
import by.intexsoft.producer.model.PublishEventDTO;
import by.intexsoft.producer.model.TelegramResultDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    MessageEntity DTOToEntity(MessageDTO source);

    TelegramResultEntity DTOToEntity(TelegramResultDTO source);

    PublishEventDTO entityToDTO(PublishEventEntity source);
}
