package com.testtask.demo.repository;


import com.testtask.demo.domain.Purchase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {

    List<Purchase> findAllByOrderByDateAsc(Pageable pageable);

    void deletePurchasesByDateIs(LocalDate date);

    List<Purchase> findAllByDate(LocalDate date);

    @Query(value = "SELECT * FROM purchases WHERE date Like %?1%", nativeQuery = true)
    List<Purchase> findAllByDateYear(@Param("yearDate") Integer yearDate);


}
