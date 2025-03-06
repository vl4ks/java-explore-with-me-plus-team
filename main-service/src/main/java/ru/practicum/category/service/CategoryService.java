package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

import java.util.Collection;

public interface CategoryService {

    public CategoryDto create(NewCategoryDto newCategoryDto);

    public Collection<CategoryDto> findAll(Integer from, Integer size);

    public CategoryDto findById(Long categoryId);

    public CategoryDto update(Long categoryId, CategoryDto categoryDto);

    public void delete(Long categoryId);
}
