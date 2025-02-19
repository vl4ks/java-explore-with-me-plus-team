package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.event.model.Location;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {

    @NotBlank
    @Size(min = 20, max = 2000, message = "Краткое описание события должно быть от 20 до 2000 симоволов.")
    private String annotation;

    @NotBlank
    private Long category;

    @NotBlank
    @Size(min = 20, max = 7000, message = "Описание события должно быть от 20 до 7000 симоволов.")
    private String description;

    @NotBlank
    private String eventDate;

    @NotBlank
    private Location location;

    private Boolean paid;

    @PositiveOrZero
    private Integer participantLimit;

    private Boolean requestModeration;

    @NotBlank
    @Size(min = 3, max = 120, message = "Заголовок события должен быть от 3 до 120 симоволов.")
    private String title;
}
