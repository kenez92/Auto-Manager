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
class ServiceTestSuite {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Test
    public void shouldAddService() {
        //given
        Service service = serviceRepository.save(createService());
        Expense expense = expenseRepository.save(createExpense());
        //when
        service.setExpense(expense);
        expense.addRService(service);
        //then
        assertEquals(LocalDate.now().plusMonths(12),
                expense.getServices().get(0).getNextServiceDate());
    }

    @Test
    public void shouldGetServiceFindById() {
        //given
        Service service = serviceRepository.save(createService());
        //when
        Service getService = serviceRepository.findById(service.getId()).orElse(null);
        //then
        assertNotNull(getService);
    }

    @Test
    public void shouldUpdateService() {
        //given
        Service service = serviceRepository.save(createService());
        //when
        service.setDistance("25000");
        //then
        assertNotEquals("15000", service.getDistance());
    }

    @Test
    public void shouldThrowExceptionForDeleteService() {
        //given
        Service service = serviceRepository.save(createService());
        //when
        //then
        assertThrows(DataIntegrityViolationException.class, () ->
                serviceRepository.existsById(service.getId()));
    }

    private Service createService() {
        return Service.builder()
                .distance("15000")
                .nextServiceDate(LocalDate.now().plusMonths(12))
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