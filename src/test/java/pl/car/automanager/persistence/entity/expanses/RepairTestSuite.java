package pl.car.automanager.persistence.entity.expanses;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.repository.ExpenseRepository;
import pl.car.automanager.persistence.repository.RepairRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class RepairTestSuite {

    @Autowired
    RepairRepository repairRepository;

    @Autowired
    ExpenseRepository expenseRepository;


    @Test
    public void shouldAddRepair() {
        //given
        Repair repair = repairRepository.save(createRepair());
        Expense expense = expenseRepository.save(createExpense());
        //when
        repair.setExpense(expense);
        expense.addRepair(repair);
        //then
        assertEquals("Default value",
                expense.getRepairs().get(0).getRepairDescription());
    }

    @Test
    public void shouldGetRepairFindById() {
        //given
        Repair repair = repairRepository.save(createRepair());
        //when
        Repair getRepair = repairRepository.findById(repair.getId()).orElse(null);
        //then
        assertNotNull(getRepair);
    }

    @Test
    public void shouldUpdateRepair() {
        //given
        Repair repair = repairRepository.save(createRepair());
        //when
        repair.setRepairDescription("Changed value");
        //then
        assertNotEquals("Default value", repair.getRepairDescription());
    }

    @Test
    public void shouldThrowExceptionForDeleteRepair() {
        //given
        Repair repair = repairRepository.save(createRepair());
        //when
        //then
        assertThrows(DataIntegrityViolationException.class, () ->
                repairRepository.existsById(repair.getId()));
    }

    private Repair createRepair() {
        return Repair.builder()
                .repairDescription("Default value")
                .expense(createExpense())
                .build();
    }

    private Expense createExpense() {
        return Expense.builder()
                .id(1L)
                .date(LocalDate.now())
                .cost(new BigDecimal("2000"))
                .insurances(new ArrayList<>())
                .refueling(new ArrayList<>())
                .registrations(new ArrayList<>())
                .repairs(new ArrayList<>())
                .services(new ArrayList<>())
                .vulcanization(new ArrayList<>())
                .build();
    }
}