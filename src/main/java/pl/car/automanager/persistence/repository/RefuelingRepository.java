package pl.car.automanager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.car.automanager.persistence.entity.expanses.Refueling;

public interface RefuelingRepository extends JpaRepository<Refueling, Long> {
}
