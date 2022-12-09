package by.intexsoft.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelegramResultDTO {

    @JsonProperty("message_id")
    private Long messageId;

    private Long date;

    private String text;

    private TelegramUserDTO from;

    private TelegramUserDTO chat;
}
