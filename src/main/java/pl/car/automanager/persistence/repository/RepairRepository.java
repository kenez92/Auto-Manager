package pl.car.automanager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.car.automanager.persistence.entity.expanses.Repair;

public interface RepairRepository extends JpaRepository<Repair, Long> {
}
