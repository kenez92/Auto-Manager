package pl.car.automanager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.car.automanager.persistence.entity.expanses.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
