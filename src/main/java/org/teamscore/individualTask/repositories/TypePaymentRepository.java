package org.teamscore.individualTask.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.teamscore.individualTask.models.entity.TypePayment;

import java.util.List;
import java.util.Optional;

public interface TypePaymentRepository extends CrudRepository<TypePayment, Long> {
    List<TypePayment> findAll(Pageable pageable);
    Optional<TypePayment> findByName(String name);
}
