package ru.practicum.user.service;

import ru.practicum.user.dto.UserDto;

public interface UserService {
    public UserDto findById(Long userId);
}
