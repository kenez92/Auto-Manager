package pl.car.automanager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.car.automanager.persistence.entity.expanses.Insurance;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}
