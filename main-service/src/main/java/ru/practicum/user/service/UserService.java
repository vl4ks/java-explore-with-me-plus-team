package ru.practicum.user.service;

import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;

import java.util.Collection;
import java.util.List;

public interface UserService {

    public UserDto create(NewUserRequest newUserRequest);

    public Collection<UserDto> findAll(List<Long> ids, Integer from, Integer size);

    public UserDto findById(Long userId);

    public void delete(Long userId);
}
