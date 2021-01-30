package pl.car.automanager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.car.automanager.persistence.entity.expanses.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
