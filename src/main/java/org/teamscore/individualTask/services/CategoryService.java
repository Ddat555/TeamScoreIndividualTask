package org.teamscore.individualTask.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.teamscore.individualTask.exceptions.CannotDeleteEntityException;
import org.teamscore.individualTask.models.DTO.entity.CategoryDTO;
import org.teamscore.individualTask.models.DTO.entity.createDTO.CreateCategoryDTO;
import org.teamscore.individualTask.models.entity.Category;
import org.teamscore.individualTask.repositories.CategoryRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO createCategory(CreateCategoryDTO categoryDTO) {
        Category category = new Category(
                categoryDTO.getName(),
                categoryDTO.getColor(),
                categoryDTO.getDescription()
        );
        return categoryRepository.save(category).toDTO();
    }

    public CategoryDTO updateCategory(CategoryDTO category) {
        var oldCategory = categoryRepository.findById(category.getId());
        if (oldCategory.isEmpty())
            return null;
        var oldCategoryPresent = oldCategory.get();
        oldCategoryPresent.setColor(category.getColor());
        oldCategoryPresent.setName(category.getName());
        oldCategoryPresent.setDescription(category.getDescription());
        return categoryRepository.save(oldCategoryPresent).toDTO();
    }

    public void deleteCategory(Long id) {
        var category = categoryRepository.findById(id).orElse(null);
        deleteCategory(category);
    }

    public void deleteCategory(Category category) {
        if (category != null) {
            if (!category.getCosts().isEmpty())
                throw new CannotDeleteEntityException(String.format("Невозможно удалить %s с ID %d, так как он используется", category.getName(), category.getId()));
            categoryRepository.delete(category);
        }
    }

    public List<CategoryDTO> getAllCategory(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream().map(Category::toDTO).toList();
    }

    public CategoryDTO getCategoryByName(String name) {
        var categoryOpt = categoryRepository.findByName(name);
        return categoryOpt.map(Category::toDTO).orElse(null);
    }
}
