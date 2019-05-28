package com.testtask.demo.repository;

import com.testtask.demo.domain.Purchase;
import com.testtask.demo.testData.AddTestUnits;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PurchaseRepositoryTest {

    private Pageable pageable;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Before
    public void setUp() throws Exception {

        AddTestUnits.generateTestPurchases(purchaseRepository);
    }

    @After
    public void tearDown() throws Exception {

        purchaseRepository.deleteAll();
    }

    @Test
    public void givenTestUnits_whenFindAllByOrderByDateAsc_thenReturnPaginationListWithSortedUnits() {

        // given

        // when
        List<Purchase> allByOrderByDateAsc = purchaseRepository.findAllByOrderByDateAsc(pageable);

        // then
        assertEquals(LocalDate.of(2016, 9, 8),
                allByOrderByDateAsc.get(0).getDate());
        assertEquals(LocalDate.of(2019, 9, 9),
                allByOrderByDateAsc.get(allByOrderByDateAsc.size() - 1).getDate());

    }

    @Test
    public void givenTestUnits_whenFindAllByOrderByDateAsc_thenReturnPaginationListWithSortedUnits_Negative() {

        // given

        // when
        List<Purchase> allByOrderByDateAsc = purchaseRepository.findAllByOrderByDateAsc(pageable);

        // then
        assertNotEquals(LocalDate.of(2016, 9, 8),
                allByOrderByDateAsc.get(allByOrderByDateAsc.size() - 1).getDate());
        assertNotEquals(LocalDate.of(2019, 9, 9),
                allByOrderByDateAsc.get(0).getDate());

    }

    @Test
    public void givenDate_whenDeletePurchasesByDate_thenNoRecordsLeftWithDeletedDate() {

        // given

        // when
        purchaseRepository.deletePurchasesByDateIs(LocalDate.of(2019, 8, 8));

        // then
        Iterable<Purchase> all = purchaseRepository.findAll();

        for (Purchase purchase : all) {

            assertNotEquals(LocalDate.of(2019, 8, 8), purchase.getDate());

        }
    }

    @Test
    public void givenDate_whenFindAllByDate_thenFindRecordsJustWithInsertedDate() {

        // given
        LocalDate date = LocalDate.of(2019, 9, 9);

        // when
        List<Purchase> allByDate = purchaseRepository.findAllByDate(date);

        // then

        assertEquals(3, allByDate.size());

        for (Purchase purchase : allByDate) {

            assertEquals(date, purchase.getDate());

        }
    }

    @Test
    public void givenYear_whenFindAllByDateYear_thenFindAllRecordsFromThisYear() {

        // given
        int year = 2016;

        // when
        List<Purchase> allByDateYear = purchaseRepository.findAllByDateYear(year);

        // then

        for (Purchase purchase : allByDateYear) {

            assertEquals(year, purchase.getDate().getYear());

        }
    }
}