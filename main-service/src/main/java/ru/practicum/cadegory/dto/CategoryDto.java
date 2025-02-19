package ru.practicum.cadegory.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    Long id;

    @NotBlank
    @Size(min = 1, max = 50, message = "Название категории должно быть от 1 до 50 симоволов.")
    private String name;
}
