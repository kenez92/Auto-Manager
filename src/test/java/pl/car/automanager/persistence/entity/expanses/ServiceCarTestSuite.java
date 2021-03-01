package pl.car.automanager.persistence.entity.expanses;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.repository.ExpenseRepository;
import pl.car.automanager.persistence.repository.ServiceRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ServiceCarTestSuite {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Test
    public void shouldAddService() {
        //given
        ServiceCar serviceCar = serviceRepository.save(createService());
        Expense expense = expenseRepository.save(createExpense());
        //when
        serviceCar.setExpense(expense);
        expense.addRService(serviceCar);
        //then
        assertEquals(LocalDate.now().plusMonths(12),
                expense.getServiceCars().get(0).getNextServiceDate());
    }

    @Test
    public void shouldGetServiceFindById() {
        //given
        ServiceCar serviceCar = serviceRepository.save(createService());
        //when
        ServiceCar getServiceCar = serviceRepository.findById(serviceCar.getId()).orElse(null);
        //then
        assertNotNull(getServiceCar);
    }

    @Test
    public void shouldUpdateService() {
        //given
        ServiceCar serviceCar = serviceRepository.save(createService());
        //when
        serviceCar.setDistance("25000");
        //then
        assertNotEquals("15000", serviceCar.getDistance());
    }

    @Test
    public void shouldThrowExceptionForDeleteService() {
        //given
        ServiceCar serviceCar = serviceRepository.save(createService());
        //when
        //then
        assertThrows(DataIntegrityViolationException.class, () ->
                serviceRepository.existsById(serviceCar.getId()));
    }

    private ServiceCar createService() {
        return ServiceCar.builder()
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
                .serviceCars(new ArrayList<>())
                .vulcanization(new ArrayList<>())
                .build();
    }
}