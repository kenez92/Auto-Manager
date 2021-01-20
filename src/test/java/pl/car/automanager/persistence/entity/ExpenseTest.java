package pl.car.automanager.persistence.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.car.automanager.persistence.entity.expanses.*;
import pl.car.automanager.persistence.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ExpenseTest {

    @Autowired
    ExpenseRepository repository;

    @Test
    public void shouldSaveNewExpanse() {
        //given
        Expense expense = createExpanse();
        //when
        Expense exp = repository.save(expense);
        Expense getExpense = repository.getOne(exp.getExpenseId());
        //then
        assertNotNull(getExpense);
    }

    @Test
    public void shouldAddInsurance() {
        //given
        Expense expense = createExpanse();
        Insurance insurance = createInsurance();
        //when
        expense.addInsurance(insurance);
        //then
        assertFalse(expense.getInsurances().isEmpty());
        assertTrue(expense.getInsurances().contains(insurance));
    }

    @Test
    public void shouldAddRefueling() {
        //given
        Expense expense = createExpanse();
        Refueling refueling = createRefueling();
        //when
        expense.addRefuel(refueling);
        //then
        assertFalse(expense.getRefueling().isEmpty());
        assertTrue(expense.getRefueling().contains(refueling));
    }

    @Test
    public void shouldAddRegistrationReview() {
        //given
        Expense expense = createExpanse();
        Registration registration = createRegistration();
        //when
        expense.addRegistration(registration);
        //then
        assertFalse(expense.getRegReviews().isEmpty());
        assertTrue(expense.getRegReviews().contains(registration));
    }

    @Test
    public void shouldAddServiceReview() {
        //given
        Expense expense = createExpanse();
        Service service = createService();
        //when
        expense.addServiceReview(service);
        //then
        assertFalse(expense.getServReviews().isEmpty());
        assertTrue(expense.getServReviews().contains(service));
    }

    @Test
    public void shouldAddRepair() {
        //given
        Expense expense = createExpanse();
        Repair repair = createRepair();
        //when
        expense.addRepairs(repair);
        //then
        assertFalse(expense.getRepairs().isEmpty());
        assertTrue(expense.getRepairs().contains(repair));
    }

    @Test
    public void shouldAddVulcanization() {
        //given
        Expense expense = createExpanse();
        Vulcanization vulcanization = createVulcanization();
        //when
        expense.addVulcanization(vulcanization);
        //then
        assertFalse(expense.getVulcanization().isEmpty());
        assertTrue(expense.getVulcanization().contains(vulcanization));
    }

    private Expense createExpanse() {
        return Expense.builder()
                .insurances(new HashSet<>())
                .refueling(new HashSet<>())
                .regReviews(new HashSet<>())
                .repairs(new HashSet<>())
                .servReviews(new HashSet<>())
                .vulcanization(new HashSet<>())
                .build();
    }


    private Insurance createInsurance() {
        return Insurance.builder()
                .cost(new BigDecimal("2000"))
                .description("OC, AC,NW")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusYears(1))
                .build();
    }

    private Refueling createRefueling() {
        return Refueling.builder()
                .cost(new BigDecimal("150"))
                .date(LocalDate.now().minusDays(1))
                .build();
    }

    private Registration createRegistration() {
        return Registration.builder()
                .faults("Brak uwag")
                .cost(new BigDecimal("100"))
                .date(LocalDate.now())
                .nextRegDate(LocalDate.now().plusYears(1).minusDays(1))
                .build();
    }

    private Service createService() {
        return Service.builder()
                .cost(new BigDecimal("1150"))
                .date(LocalDate.now())
                .distance("85450 km")
                .nextSerDate(LocalDate.now().plusYears(1))
                .build();
    }

    private Repair createRepair() {
        return Repair.builder()
                .cost(new BigDecimal("340"))
                .date(LocalDate.now().minusDays(2))
                .description("Wymiana klocków hamulcowych - komplet")
                .build();
    }

    private Vulcanization createVulcanization() {
        return Vulcanization.builder()
                .cost(new BigDecimal("120"))
                .description("Wulkanizacja")
                .date(LocalDate.now().minusDays(3))
                .build();
    }
}