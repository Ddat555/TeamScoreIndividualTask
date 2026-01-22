package org.teamscore.individualTask.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.teamscore.individualTask.models.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Category> findAll(Pageable pageable);

    Optional<Category> findByName(String name);

}
