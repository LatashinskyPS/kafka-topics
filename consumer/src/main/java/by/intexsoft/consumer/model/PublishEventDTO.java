package by.intexsoft.consumer.model;

import by.intexsoft.consumer.enums.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishEventDTO {

    private UUID uuid;

    private MessageDTO message;

    private TelegramResultDTO result;

    private State state;
}
