package pl.car.automanager.persistence.entity.expanses;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.repository.ExpenseRepository;
import pl.car.automanager.persistence.repository.RefuelingRepository;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class RefuelingTestSuite {

    @Autowired
    RefuelingRepository refuelingRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Test
    public void shouldAddRefueling() {
        //given
        Refueling refueling = refuelingRepository.save(createRefueling());
        Expense expense = expenseRepository.save(createExpense());
        //when
        refueling.setExpense(expense);
        expense.addRefueling(refueling);
        //then
        assertEquals(35.00,
                expense.getRefueling().get(0).getLiters());
    }

    @Test
    public void shouldGetRefuelingFindById() {
        //given
        Refueling refueling = refuelingRepository.save(createRefueling());
        //when
        Refueling getRefueling = refuelingRepository.findById(refueling.getId()).orElse(null);
        //then
        assertNotNull(getRefueling);
    }

    @Test
    public void shouldUpdateRefueling() {
        //given
        Refueling refueling = refuelingRepository.save(createRefueling());
        //when
        refueling.setLiters(40.00);
        //then
        assertEquals(40.00, refueling.getLiters());
    }

    @Test
    public void shouldThrowExceptionForDeleteRefueling() {
        //given
        Refueling refueling = refuelingRepository.save(createRefueling());
        //when
        //then
        assertThrows(DataIntegrityViolationException.class, () ->
                refuelingRepository.existsById(refueling.getId()));
    }

    private Refueling createRefueling() {
        return Refueling.builder()
                .liters(35.00)
                .expense(createExpense())
                .build();
    }

    private Expense createExpense() {
        return Expense.builder()
                .id(1L)
                .summaryCost(new BigDecimal("190"))
                .insurances(new ArrayList<>())
                .refueling(new ArrayList<>())
                .registrations(new ArrayList<>())
                .repairs(new ArrayList<>())
                .serviceCars(new ArrayList<>())
                .vulcanization(new ArrayList<>())
                .build();
    }
}