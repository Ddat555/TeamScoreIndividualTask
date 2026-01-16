package org.teamscore.individualTask.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.teamscore.individualTask.models.entity.Category;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    List<Category> findAll(Pageable pageable);
    Optional<Category> findByName(String name);

//    @Query("SELECT c FROM Category c where ")
//    List<Category> findAllCategoryWithCostByPeriod(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
