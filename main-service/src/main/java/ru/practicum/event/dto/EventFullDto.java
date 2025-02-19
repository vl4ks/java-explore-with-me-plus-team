package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.event.model.Location;
import ru.practicum.event.model.State;
import ru.practicum.user.dto.UserShortDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    @NotBlank
    private String annotation;

    @NotBlank
    private CategoryDto category;

    private Long confirmRequests;

    @JsonProperty("createOn")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createOn;

    private String description;

    @NotBlank
    @JsonProperty("evtntDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String evtntDate;

    private Long id;

    @NotBlank
    private UserShortDto initiator;

    private Location location;

    @NotBlank
    private Boolean paid;

    @PositiveOrZero
    private Integer participantLimit;

    private Boolean requestModeration;

    private State state;

    @NotBlank
    private String title;

    private Long views;
}
