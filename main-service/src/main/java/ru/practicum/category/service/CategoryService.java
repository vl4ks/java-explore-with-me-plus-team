package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;

import java.util.Collection;

public interface CategoryService {

    public Collection<CategoryDto> findAll(Integer from, Integer size);

    public CategoryDto findById(Long categoryId);
}
