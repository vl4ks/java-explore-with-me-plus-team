package ru.practicum.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {

    //private Set<EventShortDto> events;

    private Long id;

    private Boolean pinned;

    private String title;
}
