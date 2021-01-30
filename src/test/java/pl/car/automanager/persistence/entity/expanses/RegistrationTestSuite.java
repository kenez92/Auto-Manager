package pl.car.automanager.persistence.entity.expanses;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.car.automanager.persistence.entity.Expense;
import pl.car.automanager.persistence.repository.ExpenseRepository;
import pl.car.automanager.persistence.repository.RegistrationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class RegistrationTestSuite {

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Test
    public void shouldAddRegistration() {
        //given
        Registration registration = registrationRepository.save(createRegistration());
        Expense expense = expenseRepository.save(createExpense());
        //when
        registration.setExpense(expense);
        expense.addRegistration(registration);
        //then
        assertEquals("Default value",
                expense.getRegistrations().get(0).getFaults());
    }

    @Test
    public void shouldGetRegistrationFindById() {
        //given
        Registration registration = registrationRepository.save(createRegistration());
        //when
        Registration getRegistration = registrationRepository.findById(registration.getId()).orElse(null);
        //then
        assertNotNull(getRegistration);
    }

    @Test
    public void shouldUpdateRegistration() {
        //given
        Registration registration = registrationRepository.save(createRegistration());
        //when
        registration.setFaults("Change front tires");
        //then
        assertNotEquals("Default value", registration.getFaults());
    }

    @Test
    public void shouldThrowExceptionForDeleteRegistration() {
        //given
        Registration registration = registrationRepository.save(createRegistration());
        //when
        //then
        assertThrows(DataIntegrityViolationException.class, () ->
                registrationRepository.existsById(registration.getId()));
    }

    private Registration createRegistration() {
        return Registration.builder()
                .nextRegDate(LocalDate.now().plusYears(1).minusDays(1))
                .faults("Default value")
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