package by.intexsoft.producer.controller;

import by.intexsoft.producer.model.PublishEventDTO;
import by.intexsoft.producer.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/events")
@Tag(name = "Event Controller", description = "For operation's with events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get Event by UUID",
            parameters = {
                    @Parameter(name = "uuid", description = "UUID from event create response")
            })
    public PublishEventDTO getById(@PathVariable(value = "uuid") UUID uuid) {
        return eventService.getById(uuid);
    }
}
