package by.intexsoft.producer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "DTO for message, include chat id and message to send")
public class MessageDTO {
    @JsonProperty("chat_id")
    @Schema(description = "Chat id", defaultValue = "477183974")
    private Long chatId;

    @Schema(description = "Message to send", defaultValue = "Test")
    private String message;
}
