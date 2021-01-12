package pl.car.automanager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.car.automanager.persistence.entity.Car;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {
}
