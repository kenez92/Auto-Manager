package pl.car.automanager.persistence.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.car.automanager.persistence.entity.expanses.*;
import pl.car.automanager.persistence.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ExpenseTestSuite {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    public void shouldAddExpense(){
        //given
        Expense expense = createExpense();
        //when
        Expense newExpense = expenseRepository.save(expense);
        //then
        assertNotNull(newExpense);
    }

    @Test
    public void shouldGetExpenseFindById(){
        //given
        Expense expense = expenseRepository.save(createExpense());
        //when
        Expense getExpense = expenseRepository.findById(expense.getId()).orElse(null);
        //then
        assertNotNull(getExpense);
    }

    @Test
    public void shouldUpdateExpense(){
        //given
        Expense expense = expenseRepository.save(createExpense());
        //when
        expense.setSummaryCost(new BigDecimal("1500"));
        //then
        assertEquals("1500", expense.getSummaryCost().toString());
    }

    @Test
    public void shouldDeleteExpense(){
        //given
        Expense expense = expenseRepository.save(createExpense());
        //when
        expenseRepository.deleteById(expense.getId());
        //then
        assertFalse(expenseRepository.existsById(expense.getId()));
    }

    @Test
    public void addNewRepair(){
        //given
        Expense expense = createExpense();
        Repair repair = new Repair();
        //when
        expense.addRepair(repair);
        //then
        assertFalse(expense.getRepairs().isEmpty());
    }

    @Test
    public void addNewInsurance(){
        //given
        Expense expense = createExpense();
        Insurance insurance = new Insurance();
        //when
        expense.addInsurance(insurance);
        //then
        assertFalse(expense.getInsurances().isEmpty());
    }

    @Test
    public void addNewRefueling(){
        //given
        Expense expense = createExpense();
        Refueling refueling = new Refueling();
        //when
        expense.addRefueling(refueling);
        //then
        assertFalse(expense.getRefueling().isEmpty());
    }

    @Test
    public void addNewRegistration(){
        //given
        Expense expense = createExpense();
        Registration registration = new Registration();
        //when
        expense.addRegistration(registration);
        //then
        assertFalse(expense.getRegistrations().isEmpty());
    }

    @Test
    public void addNewService(){
        //given
        Expense expense = createExpense();
        Maintenance maintenance = new Maintenance();
        //when
        expense.addMaintenance(maintenance);
        //then
        assertFalse(expense.getMaintenances().isEmpty());
    }

    @Test
    public void addNewVulcanization(){
        //given
        Expense expense = createExpense();
        Vulcanization vulcanization = new Vulcanization();
        //when
        expense.addVulcanization(vulcanization);
        //then
        assertFalse(expense.getVulcanization().isEmpty());
    }
    private Expense createExpense() {
        return Expense.builder()
                .summaryCost(new BigDecimal("2000"))
                .insurances(new ArrayList<>())
                .refueling(new ArrayList<>())
                .registrations(new ArrayList<>())
                .repairs(new ArrayList<>())
                .maintenances(new ArrayList<>())
                .vulcanization(new ArrayList<>())
                .build();
    }
}