package pl.car.automanager.persistence.entity.expanses;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.repository.ExpenseRepository;
import pl.car.automanager.persistence.repository.InsuranceRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class InsuranceTestSuite {

    @Autowired
    InsuranceRepository insuranceRepository;
    @Autowired
    ExpenseRepository expenseRepository;

    @Test
    public void shouldAddInsurance() {
        //given &
        Insurance insurance = insuranceRepository.save(createInsurance());
        Expense expense = expenseRepository.save(createExpense());
        //when
        insurance.setExpense(expense);
        expense.addInsurance(insurance);
        //then
        assertEquals(LocalDate.now().plusYears(1).plusDays(4), expense.getInsurances().get(0).getEndDate());
    }

    @Test
    public void shouldGetInsuranceFindById() {
        //given
        Insurance insurance = insuranceRepository.save(createInsurance());
        //when
        Insurance getInsurance = insuranceRepository.findById(insurance.getId()).orElse(null);
        //then
        assertNotNull(getInsurance);
    }

    @Test
    public void shouldUpdateInsurance() {
        //given
        Insurance insurance = insuranceRepository.save(createInsurance());
        //when
        insurance.setDescription("Changed value");
        //then
        assertEquals("Changed value", insurance.getDescription());
    }

    @Test
    public void shouldThrowExceptionForDeleteInsurance() {
        //given
        Insurance insurance = insuranceRepository.save(createInsurance());
        //when
        //then
        assertThrows(DataIntegrityViolationException.class, () ->
                insuranceRepository.existsById(insurance.getId()));

    }

    private Insurance createInsurance() {
        return Insurance.builder()
                .startDate(LocalDate.now().plusDays(5))
                .endDate(LocalDate.now().plusYears(1).plusDays(4))
                .description("Default value")
                .expense(createExpense())
                .build();
    }


    private Expense createExpense() {
        return Expense.builder()
                .id(1L)
                .summaryCost(BigDecimal.ZERO)
                .insurances(new ArrayList<>())
                .refueling(new ArrayList<>())
                .registrations(new ArrayList<>())
                .repairs(new ArrayList<>())
                .maintenances(new ArrayList<>())
                .vulcanization(new ArrayList<>())
                .build();
    }
}