package pl.car.automanager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.car.automanager.persistence.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
