package pl.car.automanager.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.car.automanager.commons.enums.FuelEnum;
import pl.car.automanager.persistence.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserTestSuite {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        //Given
        User user = userRepository.save(createUser());
        Long carId = user.getId();
        //When
        User dbUser = userRepository.findById(carId).orElse(null);
        //Then
        Assertions.assertNotNull(dbUser);
    }

    @Test
    public void testFindByIdShouldThrowException() {
        //Given
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userRepository.findById(0L).orElseThrow(IllegalArgumentException::new);
        });
    }

    @Test
    public void testAddUser() {
        //Given
        User user = createUser();
        //When
        User savedUser = userRepository.save(user);
        //Then
        Assertions.assertNotNull(savedUser);
    }

    @Test
    public void testAddUserShouldThrowException() {
        //Given
        User user = createUser();
        user.setLogin(null);
        //When & Then
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(user);
        });
    }

    @Test
    public void testUpdateUser() {
        //Given
        User user = userRepository.save(createUser());
        user.setFirstName("first name");
        user.setLogin("newLogin");
        //When
        User updatedUser = userRepository.save(user);
        //Then
        Assertions.assertEquals("first name", updatedUser.getFirstName());
        Assertions.assertEquals("newLogin", updatedUser.getLogin());
        Assertions.assertNotNull(user.getCars());
    }

    @Test
    public void testDeleteUser() {
        //Given
        User user = userRepository.save(createUser());
        //When
        userRepository.delete(user);
        //Then
        Assertions.assertFalse(userRepository.existsById(user.getId()));
    }

    private Car createCar() {
        return Car.builder()
                .brand("OPEL")
                .model("VECTRA C")
                .vin("VINTESTVINTESTVINTEST")
                .engine("2.0 TDCI")
                .amountOfDoors(5)
                .fuel(FuelEnum.PETROL)
                .build();
    }

    private User createUser() {
        List<Car> cars = new ArrayList<>();
        cars.add(createCar());
        return User.builder()
                .firstName("Test Name")
                .email("test@test.pl")
                .login("Test_Login")
                .password("password")
                .cars(cars)
                .build();
    }
}
