package pl.car.automanager.persistence.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class ExpenseServiceTest {

    private ExpenseService expenseService;
    @Autowired
    private ExpenseRepository expenseRepository;

    private LocalDate expenseDate;
    private LocalDate insuranceStart;
    private LocalDate insuranceEnd;
    private BigDecimal cost;
    private String expenseDescription;
    private Double liters;
    private String distance;
    private LocalDate nextExpenseDate;
    private String faults;

    @BeforeEach
    public void setup(){
        expenseService = new ExpenseService(expenseRepository);
        expenseDate = LocalDate.now();
        insuranceStart = LocalDate.now();
        insuranceEnd = LocalDate.now().plusYears(1L);
        cost = new BigDecimal("2000");
        expenseDescription = "Default value";
        liters = 45.00D;
        distance = "115000";
        nextExpenseDate = LocalDate.now().plusYears(1L);
        faults = "Default faults in car";
    }

    @Test
    public void shouldAddRefuelingExpense(){
        //given & when
        Expense expense = expenseService.addRefuelingExpense(expenseDate,cost,
                liters);
        //then
        assertFalse(expense.getRefueling().isEmpty());
    }

    @Test
    public void shouldServiceExpense(){
        //given & when
        Expense expense = expenseService.addServiceExpense(expenseDate,cost,distance, nextExpenseDate);
        //then
        assertFalse(expense.getServices().isEmpty());
    }

    @Test
    public void shouldAddInsuranceExpense(){
        //given & when
        Expense expense = expenseService.addInsuranceExpense(expenseDate,cost,
                insuranceStart,insuranceEnd, expenseDescription);
        //then
        assertFalse(expense.getInsurances().isEmpty());
    }

    @Test
    public void shouldAddRegistrationExpense(){
        //given & when
        Expense expense = expenseService.addRegistrationExpense(expenseDate,cost,
                nextExpenseDate, faults);
        //then
        assertFalse(expense.getRegistrations().isEmpty());
    }

    @Test
    public void shouldAddRepairExpense(){
        //given & when
        Expense expense = expenseService.addRepairExpense(expenseDate,cost, expenseDescription);
        //then
        assertFalse(expense.getRepairs().isEmpty());
    }

    @Test
    public void shouldAddVulcanizationExpense(){
        //given & when
        Expense expense = expenseService.addVulcanizationExpense(expenseDate,cost, expenseDescription);
        //then
        assertFalse(expense.getVulcanization().isEmpty());
    }
}