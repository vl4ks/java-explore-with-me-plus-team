package ru.practicum.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest {

    @Email
    @NotBlank
    @Size(min = 6, max = 254, message = "Email должен быть в диапозоне от 6 до 254 симовла.")
    private String email;

    @NotBlank
    @Size(min = 2, max = 250, message = "Имя должно быть в диапозоне от 2 до 250 симовлов.")
    private String name;
}
