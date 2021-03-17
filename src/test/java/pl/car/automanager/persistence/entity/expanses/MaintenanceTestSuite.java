package pl.car.automanager.persistence.entity.expanses;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.repository.ExpenseRepository;
import pl.car.automanager.persistence.repository.MaintenanceRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MaintenanceTestSuite {

    @Autowired
    MaintenanceRepository maintenanceRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Test
    public void shouldAddService() {
        //given
        Maintenance maintenance = maintenanceRepository.save(createService());
        Expense expense = expenseRepository.save(createExpense());
        //when
        maintenance.setExpense(expense);
        expense.addMaintenance(maintenance);
        //then
        assertEquals("15000", maintenance.getDistance());
    }

    @Test
    public void shouldGetServiceFindById() {
        //given
        Maintenance maintenance = maintenanceRepository.save(createService());
        //when
        Maintenance getMaintenance = maintenanceRepository.findById(maintenance.getId()).orElse(null);
        //then
        assertNotNull(getMaintenance);
    }

    @Test
    public void shouldUpdateService() {
        //given
        Maintenance maintenance = maintenanceRepository.save(createService());
        //when
        maintenance.setDistance("25000");
        //then
        assertNotEquals("15000", maintenance.getDistance());
    }

    @Test
    public void shouldThrowExceptionForDeleteService() {
        //given
        Maintenance maintenance = maintenanceRepository.save(createService());
        //when
        //then
        assertThrows(DataIntegrityViolationException.class, () ->
                maintenanceRepository.existsById(maintenance.getId()));
    }

    private Maintenance createService() {
        return Maintenance.builder()
                .distance("15000")
                .nextServiceDate(LocalDate.now().plusMonths(12))
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
                .maintenances(new ArrayList<>())
                .vulcanization(new ArrayList<>())
                .build();
    }
}