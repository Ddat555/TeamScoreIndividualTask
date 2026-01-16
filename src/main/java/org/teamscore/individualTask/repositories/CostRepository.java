package org.teamscore.individualTask.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.teamscore.individualTask.models.entity.Category;
import org.teamscore.individualTask.models.entity.Cost;
import org.teamscore.individualTask.models.entity.TypePayment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CostRepository extends CrudRepository<Cost, Long> {
    List<Cost> findAll(Pageable pageable);
    @Query("SELECT c FROM Cost c WHERE c.dateTimePay BETWEEN :from AND :to")
    List<Cost> findAllByPeriod(@Param("from") LocalDateTime from,
                               @Param("to")LocalDateTime to);

//    @Query("SELECT c FROM Cost c Where c.dateTimePay BETWEEN :from and :to and c.type_payment = :type_p")
//    List<Cost> findAllByTypePaymentAndPeriod(@Param("type_p") TypePayment typePayment, @Param("from") LocalDate from, @Param("to") LocalDate to);

//    @Query("SELECT c FROM Cost c Where c.dateTimePay BETWEEN :from and :to and :cat in c.categories")
//    List<Cost> findAllByCategoryAndPeriod(@Param("cat") Category category, @Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("""
            SELECT cat, SUM(c.sum) FROM Cost c
             JOIN c.categories cat
             WHERE c.dateTimePay BETWEEN :from and :to
             GROUP BY cat
             ORDER BY SUM(c.sum) DESC
            """)
    List<Object[]> getCategorySums(@Param("from") LocalDateTime from,
                                   @Param("to") LocalDateTime to);

    @Query("""
            SELECT typ, SUM(c.sum) FROM Cost c
             JOIN c.typePayment typ
             WHERE c.dateTimePay BETWEEN :from and :to
             GROUP BY typ
             ORDER BY SUM(c.sum) DESC
            """)
    List<Object[]> getTypePaymentSums(@Param("from") LocalDateTime from,
                                    @Param("to") LocalDateTime to);

    @Query("""
            SELECT c.sellerName, SUM(c.sum) FROM Cost c
             WHERE c.dateTimePay BETWEEN :from and :to
             GROUP BY c.sellerName
             ORDER BY SUM(c.sum) DESC
            """)
    List<Object[]> getSellersSums(@Param("from") LocalDateTime from,
                                      @Param("to") LocalDateTime to);

}
