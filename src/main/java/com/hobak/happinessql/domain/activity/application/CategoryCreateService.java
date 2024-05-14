package com.hobak.happinessql.domain.activity.application;

import com.hobak.happinessql.domain.activity.converter.CategoryConverter;
import com.hobak.happinessql.domain.activity.domain.Category;
import com.hobak.happinessql.domain.activity.dto.CategoryResponseDto;
import com.hobak.happinessql.domain.activity.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryCreateService {
    private final CategoryRepository categoryRepository;
    public CategoryResponseDto createCategory(Long userId){
        Category category = CategoryConverter.toCategory(userId);
        categoryRepository.save(category);
        return CategoryConverter.toCategoryResponseDto(category.getCategoryId());
    }
}
