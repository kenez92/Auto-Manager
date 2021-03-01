package pl.car.automanager.persistence.entity.expanses;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.repository.ExpenseRepository;
import pl.car.automanager.persistence.repository.VulcanizationRepository;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class VulcanizationTest {

    @Autowired
    VulcanizationRepository vulcanizationRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Test
    public void shouldAddRepair() {
        //given
        Vulcanization vulcanization = vulcanizationRepository.save(createVulcanization());
        Expense expense = expenseRepository.save(createExpense());
        //when
        vulcanization.setExpense(expense);
        expense.addVulcanization(vulcanization);
        //then
        assertEquals("Default value",
                expense.getVulcanization().get(0).getDescription());
    }

    @Test
    public void shouldGetRepairFindById() {
        //given
        Vulcanization vulcanization = vulcanizationRepository.save(createVulcanization());
        //when
        Vulcanization getVulcanization = vulcanizationRepository.findById(vulcanization.getId()).orElse(null);
        //then
        assertNotNull(getVulcanization);
    }

    @Test
    public void shouldUpdateRepair() {
        //given
        Vulcanization vulcanization = vulcanizationRepository.save(createVulcanization());
        //when
        vulcanization.setDescription("Changed value");
        //then
        assertNotEquals("Default value", vulcanization.getDescription());
    }

    @Test
    public void shouldThrowExceptionForDeleteRepair() {
        //given
        Vulcanization vulcanization = vulcanizationRepository.save(createVulcanization());
        //when
        //then
        assertThrows(DataIntegrityViolationException.class, () ->
                vulcanizationRepository.existsById(vulcanization.getId()));
    }

    private Vulcanization createVulcanization() {
        return Vulcanization.builder()
                .description("Default value")
                .expense(createExpense())
                .build();
    }

    private Expense createExpense() {
        return Expense.builder()
                .id(1L)
                .summaryCost(new BigDecimal("2000"))
                .insurances(new ArrayList<>())
                .refueling(new ArrayList<>())
                .registrations(new ArrayList<>())
                .repairs(new ArrayList<>())
                .serviceCars(new ArrayList<>())
                .vulcanization(new ArrayList<>())
                .build();
    }

}