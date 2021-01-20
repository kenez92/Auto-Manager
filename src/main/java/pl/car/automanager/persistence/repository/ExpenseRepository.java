package pl.car.automanager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.car.automanager.persistence.entity.Expense;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
