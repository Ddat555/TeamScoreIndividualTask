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
    @Autowired
    private ConverterDTOService converterDTOService;

    public CategoryDTO createCategory(CreateCategoryDTO categoryDTO) {
        Category category = new Category(
                categoryDTO.getName(),
                categoryDTO.getColor(),
                categoryDTO.getDescription()
        );
        return converterDTOService.categoryConvert(categoryRepository.save(category));
    }

    public CategoryDTO updateCategory(CategoryDTO category) {
        var oldCategory = categoryRepository.findById(category.getId());
        if (oldCategory.isEmpty())
            return null;
        var oldCategoryPresent = oldCategory.get();
        oldCategoryPresent.setColor(category.getColor());
        oldCategoryPresent.setName(category.getName());
        oldCategoryPresent.setDescription(category.getDescription());
        return converterDTOService.categoryConvert(categoryRepository.save(oldCategoryPresent));
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
        return categoryRepository.findAll(pageable).stream().map(cat -> converterDTOService.categoryConvert(cat)).toList();
    }

    public CategoryDTO getCategoryByName(String name) {
        return converterDTOService.categoryConvert(categoryRepository.findByName(name).orElse(null));
    }
}
