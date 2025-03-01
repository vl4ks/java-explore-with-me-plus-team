package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {

    private String annotation;

    private CategoryDto category;

    private Long confirmRequests;

    private String eventDate;

    private Long id;

    private UserShortDto initiator;

    private Boolean paid;

    private String title;

    private Long views;
}
