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
public class TelegramResponse {
    private Boolean ok;

    private TelegramResultDTO result;

    @JsonProperty("error_code")
    private Long errorCode;

    private ParametersDTO parameters;
}
