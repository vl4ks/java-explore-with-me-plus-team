package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.cadegory.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {

    @NotBlank
    private String annotation;

    @NotBlank
    private CategoryDto category;

    private Long confirmRequests;

    @NotBlank
    @JsonProperty("eventDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String eventDate;

    private Long id;

    @NotBlank
    private UserShortDto initiator;

    private Boolean paid;

    @NotBlank
    private String title;

    private Long views;
}
