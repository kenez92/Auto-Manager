package pl.car.automanager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.car.automanager.persistence.entity.expanses.Vulcanization;

public interface VulcanizationRepository extends JpaRepository<Vulcanization, Long> {
}
