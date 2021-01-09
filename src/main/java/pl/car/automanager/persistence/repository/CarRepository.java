package pl.car.automanager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.car.automanager.persistence.entity.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
