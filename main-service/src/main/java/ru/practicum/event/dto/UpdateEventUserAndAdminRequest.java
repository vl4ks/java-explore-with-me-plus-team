package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.event.model.Location;
import ru.practicum.event.model.StateAction;

//Разница между UpdateEventUserRequest и UpdateEventAdminRequest только в стататусе stateAction
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserAndAdminRequest {

    private String annotation;

    private Long category;

    private String description;

    @JsonProperty("eventDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String eventDate;

    private Location location;

    private Boolean paid;

    @PositiveOrZero
    private Integer participantLimit;

    private Boolean requestModeration;

    private StateAction stateAction;

    private String title;
}
