package pl.car.automanager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.car.automanager.persistence.entity.expanses.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
}
